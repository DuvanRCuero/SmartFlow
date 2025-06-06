package com.example.smartflow.di

import android.content.Context
import androidx.room.Room
import com.example.smartflow.BuildConfig
import com.example.smartflow.data.local.AppDatabase
import com.example.smartflow.data.local.dao.UserDao
import com.example.smartflow.data.local.preferences.AuthPreferences
import com.example.smartflow.data.remote.api.AgentApi
import com.example.smartflow.data.remote.api.AuthApi
import com.example.smartflow.data.remote.api.LogApi
import com.example.smartflow.data.remote.api.SuggestionApi
import com.example.smartflow.data.remote.api.TaskApi
import com.example.smartflow.data.remote.repository.AgentRepositoryImpl
import com.example.smartflow.data.remote.repository.LogRepositoryImpl
import com.example.smartflow.data.remote.repository.SuggestionRepositoryImpl
import com.example.smartflow.data.remote.repository.TaskRepositoryImpl
import com.example.smartflow.data.repository.AuthRepositoryImpl
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.data.remote.repository.AgentRepository
import com.example.smartflow.data.remote.repository.LogRepository
import com.example.smartflow.data.remote.repository.SuggestionRepository
import com.example.smartflow.data.remote.repository.TaskRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ------------------------------------------------------------
    // 1) Base de datos local (Room) y UserDao
    // ------------------------------------------------------------
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "smartflow_local_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    // ------------------------------------------------------------
    // 2) AuthPreferences (DataStore para guardar userId / token)
    // ------------------------------------------------------------
    @Provides
    @Singleton
    fun provideAuthPreferences(@ApplicationContext context: Context): AuthPreferences {
        return AuthPreferences(context)
    }

    // ------------------------------------------------------------
    // 3) Retrofit + APIs remotas
    // ------------------------------------------------------------
    @Provides @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .client(okHttp)                    // ← usa el cliente con logging
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType())
            )
            .build()

    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder.build()
    }



    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideTaskApi(retrofit: Retrofit): TaskApi =
        retrofit.create(TaskApi::class.java)

    @Provides
    @Singleton
    fun provideLogApi(retrofit: Retrofit): LogApi =
        retrofit.create(LogApi::class.java)

    @Provides
    @Singleton
    fun provideSuggestionApi(retrofit: Retrofit): SuggestionApi =
        retrofit.create(SuggestionApi::class.java)

    @Provides
    @Singleton
    fun provideAgentApi(retrofit: Retrofit): AgentApi =
        retrofit.create(AgentApi::class.java)

    // ------------------------------------------------------------
    // 4) Repositorios (inyectar implementaciones concretas)
    // ------------------------------------------------------------
    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        userDao: UserDao,
        prefs: AuthPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(api, userDao, prefs)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        api: TaskApi,
        prefs: AuthPreferences
    ): TaskRepository {
        return TaskRepositoryImpl(api, prefs)
    }

    @Provides
    @Singleton
    fun provideLogRepository(
        api: LogApi,
        prefs: AuthPreferences
    ): LogRepository {
        return LogRepositoryImpl(api, prefs)
    }

    @Provides
    @Singleton
    fun provideSuggestionRepository(
        api: SuggestionApi,
        prefs: AuthPreferences
    ): SuggestionRepository {
        return SuggestionRepositoryImpl(api, prefs)
    }

    @Provides
    @Singleton
    fun provideAgentRepository(
        api: AgentApi,
        prefs: AuthPreferences
    ): AgentRepository {
        return AgentRepositoryImpl(api, prefs)
    }
}