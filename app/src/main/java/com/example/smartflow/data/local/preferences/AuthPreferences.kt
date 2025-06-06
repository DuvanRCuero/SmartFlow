// app/src/main/java/com/example/smartflow/data/local/preferences/AuthPreferences.kt

package com.example.smartflow.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.*
import java.io.IOException

/**
 * AuthPreferences: guarda el userId (y opcionalmente un token) en DataStore.
 * Ahora expone userIdFlow e isLoggedInFlow, y añadimos un helper getUserId().
 */
class AuthPreferences(private val context: Context) {

    // Delegado para crear un DataStore de Preferences
    private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

    companion object {
        private val KEY_USER_ID = stringPreferencesKey("key_user_id")
        private val KEY_JWT_TOKEN = stringPreferencesKey("key_jwt_token")
    }

    /**
     * Flow que emite continuamente el userId (o "" si no hay ninguno).
     */
    val userIdFlow: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            prefs[KEY_USER_ID] ?: ""
        }

    /**
     * Flow que emite true si userIdFlow no está vacío, false en caso contrario.
     */
    val isLoggedInFlow: Flow<Boolean> = userIdFlow.map { it.isNotBlank() }

    /**
     * Helper para leer “una sola vez” el userId actual.
     * Esto simplifica a que tus repositorios sigan llamando a prefs.getUserId().
     */
    suspend fun getUserId(): String {
        return userIdFlow.first()
    }

    /**
     * Guarda (o actualiza) el userId en DataStore.
     */
    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_ID] = userId
        }
    }

    /**
     * (Opcional) Guarda un token JWT en DataStore.
     */
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_JWT_TOKEN] = token
        }
    }

    /**
     * (Opcional) Obtiene el token JWT guardado o "" si no hay ninguno.
     */
    suspend fun getToken(): String {
        return context.dataStore.data
            .map { prefs -> prefs[KEY_JWT_TOKEN] ?: "" }
            .first()
    }

    /**
     * Borra todos los datos de sesión (userId y token).
     */
    suspend fun clearUserData() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
