// data/local/preferences/AuthPreferences.kt - Add this method
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

    // Existing async methods
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

    val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    val userId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_ID_KEY]
    }

    suspend fun clearAuth() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(USER_ID_KEY)
            preferences.remove(USER_EMAIL_KEY)
            preferences.remove(USER_NAME_KEY)
        }
    }

    suspend fun isLoggedIn(): Boolean {
        return authToken.first() != null
    }

    // ADD THIS METHOD for synchronous token access in OkHttp interceptor
    fun getTokenSync(): String? {
        return runBlocking {
            try {
                authToken.first()
            } catch (e: Exception) {
                null
            }
        }
    }
}