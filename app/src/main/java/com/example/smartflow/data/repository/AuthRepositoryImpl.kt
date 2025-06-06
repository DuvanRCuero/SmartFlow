package com.example.smartflow.data.repository

import com.example.smartflow.data.local.dao.UserDao
import com.example.smartflow.data.local.entity.UserEntity
import com.example.smartflow.data.local.preferences.AuthPreferences
import com.example.smartflow.data.remote.api.AuthApi
import com.example.smartflow.data.remote.dto.AuthRequest
import com.example.smartflow.data.remote.dto.SignupRequest
import com.example.smartflow.domain.model.User
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación de AuthRepository que intenta primero autenticar localmente (Room/DAO),
 * y si falla, recurre al endpoint remoto (AuthApi). Luego guarda el userId en
 * AuthPreferences (DataStore).
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val userDao: UserDao,
    private val authPreferences: AuthPreferences
) : AuthRepository {

    /**
     * Para simplicidad, hacemos hash usando el hashCode de la cadena.
     * En producción, usa un algoritmo seguro (p.ej. Bcrypt).
     */
    private fun hashPassword(password: String): String {
        return password.hashCode().toString()
    }

    /**
     * Convierte un UserEntity de la base local a la entidad de dominio User.
     */
    private fun mapUserEntityToDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            email = entity.email,
            name = entity.name,
            timeZone = entity.tz,
            createdAt = entity.created_at
        )
    }

    override suspend fun login(email: String, password: String): Resource<User> {
        return try {
            val passwordHash = hashPassword(password)

            // 1) Intentamos login local (Room) primero:
            val localUser = userDao.getUserByCredentials(email, passwordHash)
            if (localUser != null) {
                // Si existe en local, guardamos sólo el userId en DataStore
                authPreferences.saveUserId(localUser.id.toString())
                return Resource.Success(mapUserEntityToDomain(localUser))
            }

            // 2) Si no existe localmente, pedimos al servidor remoto:
            val response = authApi.login(AuthRequest(email = email, password = password))
            if (response.success && response.userId.isNotBlank()) {

                // Insertamos (o actualizamos) el UserEntity local
                val userEntity = UserEntity(
                    id = UUID.fromString(response.userId),
                    email = response.email,
                    name = response.name,
                    tz = ZonedDateTime.now().zone.id,
                    passwordHash = passwordHash
                )
                userDao.insertUser(userEntity)

                // Guardamos el userId en DataStore
                authPreferences.saveUserId(response.userId)
                return Resource.Success(mapUserEntityToDomain(userEntity))
            } else {
                Resource.Error(response.message ?: "Login remoto falló")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error en login de AuthRepositoryImpl")
            Resource.Error("Error al conectarse al servidor o en la base de datos: ${e.message}")
        }
    }

    override suspend fun signup(
        email: String,
        password: String,
        name: String?,
        timeZone: String
    ): Resource<User> {
        return try {
            // 1) Verificamos si ya existe el usuario en local:
            val existingLocal = userDao.getUserByEmail(email)
            if (existingLocal != null) {
                return Resource.Error("Ya existe un usuario con ese correo")
            }

            // 2) Intentamos el signup remoto
            try {
                val response = authApi.signup(
                    SignupRequest(
                        email = email,
                        password = password,
                        name = name,
                        tz = timeZone
                    )
                )

                if (response.success && response.userId.isNotBlank()) {
                    // Creamos la entidad local
                    val passwordHash = hashPassword(password)
                    val userEntity = UserEntity(
                        id = UUID.fromString(response.userId),
                        email = response.email,
                        name = response.name,
                        tz = timeZone,
                        passwordHash = passwordHash
                    )
                    userDao.insertUser(userEntity)

                    // Guardamos sólo el userId en DataStore
                    authPreferences.saveUserId(response.userId)
                    return Resource.Success(mapUserEntityToDomain(userEntity))
                } else {
                    return Resource.Error(response.message ?: "Signup remoto falló")
                }
            } catch (remoteEx: Exception) {
                Timber.e(remoteEx, "Error en signup remoto, creamos usuario solo local")
                // Si no hay conexión, creamos solo localmente:
                val passwordHash = hashPassword(password)
                val newLocalUser = UserEntity(
                    email = email,
                    name = name,
                    tz = timeZone,
                    passwordHash = passwordHash
                )
                val insertedId = userDao.insertUser(newLocalUser)
                return if (insertedId > 0) {
                    // Guardamos localmente en DataStore
                    val generatedId = newLocalUser.id.toString()
                    authPreferences.saveUserId(generatedId)
                    Resource.Success(mapUserEntityToDomain(newLocalUser))
                } else {
                    Resource.Error("No se pudo crear el usuario localmente")
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error en signup de AuthRepositoryImpl")
            Resource.Error("Error al registrarse: ${e.message}")
        }
    }

    override fun getCurrentUser(): Flow<User?> {
        // Observa el flujo de userId en DataStore; cuando cambie, buscamos en la base local
        return authPreferences.userIdFlow
            .map { idString ->
                if (idString.isNotBlank()) {
                    val uuid = UUID.fromString(idString)
                    val userEntity = userDao.getUserById(uuid).firstOrNull()
                    userEntity?.let { mapUserEntityToDomain(it) }
                } else {
                    null
                }
            }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        // Solo devolvemos el flujo isLoggedInFlow que definimos en AuthPreferences
        return authPreferences.isLoggedInFlow
    }

    override suspend fun logout() {
        // Limpiamos la sesión en DataStore
        authPreferences.clearUserData()
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String
    ): Result<Unit> {
        return try {
            val tz = ZoneId.systemDefault().id
            when (val res = signup(email, password, name, tz)) {
                is Resource.Success -> Result.success(Unit)
                is Resource.Error   -> Result.failure(Exception(res.message))
                Resource.Loading    -> Result.failure(Exception("Estado inesperado: Loading"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
