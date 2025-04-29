package com.example.smartflow.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

@Singleton
class AuthPreferences @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        private val USER_ID_KEY    = stringPreferencesKey("user_id")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_NAME_KEY  = stringPreferencesKey("user_name")
    }

    // Save user session after successful login
    suspend fun saveUserSession(userId: String, email: String, name: String?) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY]    = userId
            prefs[USER_EMAIL_KEY] = email
            name?.let { prefs[USER_NAME_KEY] = it }
        }
    }

    // Get user ID
    val userId: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_ID_KEY]
    }

    // Get user email
    val userEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_EMAIL_KEY]
    }

    // Get user name
    val userName: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_NAME_KEY]
    }

    // Check if user is logged in
    val isLoggedIn: Flow<Boolean> = userId.map { it != null }

    // Clear session on logout
    suspend fun clearUserSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
            prefs.remove(USER_EMAIL_KEY)
            prefs.remove(USER_NAME_KEY)
        }
    }
}
