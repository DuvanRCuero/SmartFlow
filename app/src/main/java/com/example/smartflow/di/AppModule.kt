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
import okhttp3.Interceptor
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    companion object {
        // Network Configuration - UPDATE WITH YOUR WINDOWS IP
        private const val WINDOWS_IP = "192.168.56.1" 
        private const val BASE_URL_EMULATOR = "http://10.0.2.2:8000/"
        private const val BASE_URL_DEVICE = "http://$WINDOWS_IP:8000/"

        private fun isEmulator(): Boolean {
            return (android.os.Build.FINGERPRINT.startsWith("generic")
                    || android.os.Build.FINGERPRINT.startsWith("unknown")
                    || android.os.Build.MODEL.contains("google_sdk")
                    || android.os.Build.MODEL.contains("Emulator")
                    || android.os.Build.MODEL.contains("Android SDK built for x86"))
        }

        private val BASE_URL = if (isEmulator()) BASE_URL_EMULATOR else BASE_URL_DEVICE
    }

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
    // 3) Retrofit + APIs remotas with Authentication Interceptor
    // ------------------------------------------------------------
    @Provides @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Use dynamic URL based on device type
            .client(okHttp)
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }.asConverterFactory("application/json".toMediaType())
            )
            .build()

    @Provides @Singleton
    fun provideOkHttpClient(authPreferences: AuthPreferences): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // Add authentication interceptor
        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()

            // Get token from preferences (this will be synchronous for simplicity)
            // In production, you might want to handle this differently
            val token = runCatching {
                // You'll need to implement a synchronous way to get token
                // or use a different approach for auth headers
                authPreferences.getTokenSync() // You'll need to implement this
            }.getOrNull()

            val newRequest = if (!token.isNullOrEmpty()) {
                originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                originalRequest
            }

            chain.proceed(newRequest)
        }

        builder.addInterceptor(authInterceptor)

        // Add logging only in debug
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor { message ->
                Timber.tag("SmartFlow-API").d(message)
            }
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        // Add connection timeouts
        builder.connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        builder.readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        builder.writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)

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