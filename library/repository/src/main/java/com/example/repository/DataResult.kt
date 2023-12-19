package com.example.repository

sealed interface DataResult<T> {
    data class Success<T>(
        val data: T,
        val isOfflineCache: Boolean,
    ) : DataResult<T>

    data class Error<T>(
        val throwable: Throwable?
    ) : DataResult<T>
}