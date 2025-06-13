// data/di/NetworkModule.kt
package com.example.smartflow.data.di

import android.os.Build
import com.example.smartflow.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.example.smartflow.data.local.preferences.AuthPreferences
import com.example.smartflow.data.remote.api.AgentApi
import com.example.smartflow.data.remote.api.AuthApi
import com.example.smartflow.data.remote.api.LogApi
import com.example.smartflow.data.remote.api.SuggestionApi
import com.example.smartflow.data.remote.api.TaskApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // ← change this to your PC’s LAN IP for real-device testing
    private const val WINDOWS_IP        = "192.168.1.42"
    private const val BASE_URL_EMULATOR = "http://10.0.2.2:8000/"
    private const val BASE_URL_DEVICE   = "http://$WINDOWS_IP:8000/"

    private fun isEmulator(): Boolean =
        Build.FINGERPRINT.startsWith("generic") ||
                Build.FINGERPRINT.startsWith("unknown") ||
                Build.MODEL.contains("google_sdk") ||
                Build.MODEL.contains("Emulator") ||
                Build.MODEL.contains("Android SDK built for x86")

    private fun getBaseUrl(): String =
        if (isEmulator()) BASE_URL_EMULATOR else BASE_URL_DEVICE

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            coerceInputValues  = true
            isLenient          = true
        }

    @Provides
    @Singleton
    fun provideContentType(): MediaType =
        "application/json".toMediaType()

    @Provides
    @Singleton
    fun provideOkHttpClient(prefs: AuthPreferences): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val token = runCatching { prefs.getTokenSync() }.getOrNull()
            val reqBuilder = chain.request().newBuilder()
            if (!token.isNullOrEmpty()) {
                reqBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(reqBuilder.build())
        }

        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttp: OkHttpClient,
        json: Json,
        contentType: MediaType
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(okHttp)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

    @Provides @Singleton fun provideAuthApi(retrofit: Retrofit): AuthApi       = retrofit.create(AuthApi::class.java)
    @Provides @Singleton fun provideTaskApi(retrofit: Retrofit): TaskApi       = retrofit.create(TaskApi::class.java)
    @Provides @Singleton fun provideLogApi(retrofit: Retrofit): LogApi         = retrofit.create(LogApi::class.java)
    @Provides @Singleton fun provideSuggestionApi(retrofit: Retrofit): SuggestionApi = retrofit.create(SuggestionApi::class.java)
    @Provides @Singleton fun provideAgentApi(retrofit: Retrofit): AgentApi     = retrofit.create(AgentApi::class.java)
}
