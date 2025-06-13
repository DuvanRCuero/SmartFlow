// di/AppModule.kt
package com.example.smartflow.di

import android.content.Context
import androidx.room.Room
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "smartflow_local_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao =
        db.userDao()

    @Provides
    @Singleton
    fun provideAuthPreferences(@ApplicationContext ctx: Context): AuthPreferences =
        AuthPreferences(ctx)

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        userDao: UserDao,
        prefs: AuthPreferences
    ): AuthRepository =
        AuthRepositoryImpl(api, userDao, prefs)

    @Provides
    @Singleton
    fun provideTaskRepository(
        api: TaskApi,
        prefs: AuthPreferences
    ): TaskRepository =
        TaskRepositoryImpl(api, prefs)

    @Provides
    @Singleton
    fun provideLogRepository(
        api: LogApi,
        prefs: AuthPreferences
    ): LogRepository =
        LogRepositoryImpl(api, prefs)

    @Provides
    @Singleton
    fun provideSuggestionRepository(
        api: SuggestionApi,
        prefs: AuthPreferences
    ): SuggestionRepository =
        SuggestionRepositoryImpl(api, prefs)

    @Provides
    @Singleton
    fun provideAgentRepository(
        api: AgentApi,
        prefs: AuthPreferences
    ): AgentRepository =
        AgentRepositoryImpl(api, prefs)
}
