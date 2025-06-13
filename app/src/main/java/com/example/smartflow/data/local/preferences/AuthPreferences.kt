package com.example.smartflow.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AuthPreferences(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("auth_prefs")
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    // ------------------------------------------------------------
    // Save Methods
    // ------------------------------------------------------------
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveUserInfo(userId: String, email: String, name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_NAME_KEY] = name
        }
    }

    suspend fun saveCompleteAuth(token: String, userId: String, email: String, name: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences[USER_ID_KEY] = userId
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_NAME_KEY] = name
        }
    }

    // ------------------------------------------------------------
    // Flow-based getters (reactive)
    // ------------------------------------------------------------
    val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    val userId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_ID_KEY]
    }

    val userEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL_KEY]
    }

    val userName: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME_KEY]
    }

    // ------------------------------------------------------------
    // Synchronous getters (for interceptors and immediate use)
    // ------------------------------------------------------------
    fun getTokenSync(): String? {
        return runBlocking {
            try {
                authToken.first()
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getUserIdSync(): String? {
        return runBlocking {
            try {
                userId.first()
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getUserEmailSync(): String? {
        return runBlocking {
            try {
                userEmail.first()
            } catch (e: Exception) {
                null
            }
        }
    }

    // ------------------------------------------------------------
    // Utility methods
    // ------------------------------------------------------------
    suspend fun clearAuth() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(USER_ID_KEY)
            preferences.remove(USER_EMAIL_KEY)
            preferences.remove(USER_NAME_KEY)
        }
    }

    suspend fun isLoggedIn(): Boolean {
        return try {
            val token = authToken.first()
            val userId = userId.first()
            !token.isNullOrEmpty() && !userId.isNullOrEmpty()
        } catch (e: Exception) {
            false
        }
    }

    fun isLoggedInSync(): Boolean {
        return try {
            val token = getTokenSync()
            val userId = getUserIdSync()
            !token.isNullOrEmpty() && !userId.isNullOrEmpty()
        } catch (e: Exception) {
            false
        }
    }

    // ------------------------------------------------------------
    // Get complete user info
    // ------------------------------------------------------------
    suspend fun getCurrentUser(): UserInfo? {
        return try {
            val token = authToken.first()
            val id = userId.first()
            val email = userEmail.first()
            val name = userName.first()

            if (!token.isNullOrEmpty() && !id.isNullOrEmpty()) {
                UserInfo(
                    id = id,
                    email = email ?: "",
                    name = name ?: "",
                    token = token
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}

// Data class for complete user info
data class UserInfo(
    val id: String,
    val email: String,
    val name: String,
    val token: String
)