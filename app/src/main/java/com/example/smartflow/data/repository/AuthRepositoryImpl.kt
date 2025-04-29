package com.example.smartflow.data.repository

import com.example.smartflow.data.local.dao.UserDao
import com.example.smartflow.data.local.entity.UserEntity
import com.example.smartflow.data.preferences.AuthPreferences
import com.example.smartflow.data.remote.api.AuthApi
import com.example.smartflow.data.remote.dto.AuthRequest
import com.example.smartflow.data.remote.dto.SignupRequest
import com.example.smartflow.domain.model.User
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val userDao: UserDao,
    private val authPreferences: AuthPreferences
) : AuthRepository {

    /**
     * For a real app, you would hash the password with a secure algorithm
     * This is just a placeholder for demo purposes
     */
    private fun hashPassword(password: String): String {
        return password.hashCode().toString()
    }

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
        try {
            // Try local login first
            val passwordHash = hashPassword(password)
            val localUser = userDao.getUserByCredentials(email, passwordHash)

            if (localUser != null) {
                // Local auth successful
                authPreferences.saveUserSession(
                    localUser.id.toString(),
                    localUser.email,
                    localUser.name
                )
                return Resource.Success(mapUserEntityToDomain(localUser))
            }

            // Try remote login
            try {
                val response = authApi.login(AuthRequest(email, password))

                if (response.success && response.userId.isNotBlank()) {
                    // Create or update local user
                    val user = UserEntity(
                        id = UUID.fromString(response.userId),
                        email = response.email,
                        name = response.name,
                        tz = ZonedDateTime.now().zone.id,
                        passwordHash = passwordHash
                    )
                    userDao.insertUser(user)

                    // Save session
                    authPreferences.saveUserSession(
                        response.userId,
                        response.email,
                        response.name
                    )

                    return Resource.Success(mapUserEntityToDomain(user))
                } else {
                    return Resource.Error(response.message ?: "Login failed")
                }
            } catch (e: Exception) {
                Timber.e(e, "Remote login failed")
                return Resource.Error("Unable to connect to server: ${e.message}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Login error")
            return Resource.Error("Login failed: ${e.message}")
        }
    }

    override suspend fun signup(
        email: String,
        password: String,
        name: String?,
        timeZone: String
    ): Resource<User> {
        try {
            // First check if user already exists locally
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                return Resource.Error("User with this email already exists")
            }

            // Try remote signup
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
                    // Create local user
                    val passwordHash = hashPassword(password)
                    val user = UserEntity(
                        id = UUID.fromString(response.userId),
                        email = response.email,
                        name = response.name,
                        tz = timeZone,
                        passwordHash = passwordHash
                    )
                    userDao.insertUser(user)

                    // Save session
                    authPreferences.saveUserSession(
                        response.userId,
                        response.email,
                        response.name
                    )

                    return Resource.Success(mapUserEntityToDomain(user))
                } else {
                    return Resource.Error(response.message ?: "Signup failed")
                }
            } catch (e: Exception) {
                Timber.e(e, "Remote signup failed")

                // If no connection, create local user only
                val passwordHash = hashPassword(password)
                val newUser = UserEntity(
                    email = email,
                    name = name,
                    tz = timeZone,
                    passwordHash = passwordHash
                )

                val userId = userDao.insertUser(newUser)
                if (userId > 0) {
                    // Save to preferences
                    authPreferences.saveUserSession(
                        newUser.id.toString(),
                        newUser.email,
                        newUser.name
                    )

                    return Resource.Success(mapUserEntityToDomain(newUser))
                } else {
                    return Resource.Error("Failed to create local user")
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Signup error")
            return Resource.Error("Signup failed: ${e.message}")
        }
    }

    override fun getCurrentUser(): Flow<User?> {
        return authPreferences.userId.map { userId ->
            userId?.let {
                val userEntity = userDao.getUserById(UUID.fromString(it)).first()
                userEntity?.let { entity -> mapUserEntityToDomain(entity) }
            }
        }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return authPreferences.isLoggedIn
    }

    override suspend fun logout() {
        authPreferences.clearUserSession()
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
                Resource.Loading -> TODO()
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}