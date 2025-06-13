// util/Resource.kt
package com.example.smartflow.util

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

// Extension functions for easier handling
inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}

inline fun <T> Resource<T>.onError(action: (String) -> Unit): Resource<T> {
    if (this is Resource.Error) action(message)
    return this
}

inline fun <T> Resource<T>.onLoading(action: () -> Unit): Resource<T> {
    if (this is Resource.Loading) action()
    return this
}

// Fold function similar to Result
inline fun <T, R> Resource<T>.fold(
    onSuccess: (T) -> R,
    onError: (String) -> R,
    onLoading: () -> R = { throw IllegalStateException("Loading state not handled") }
): R {
    return when (this) {
        is Resource.Success -> onSuccess(data)
        is Resource.Error -> onError(message)
        is Resource.Loading -> onLoading()
    }
}