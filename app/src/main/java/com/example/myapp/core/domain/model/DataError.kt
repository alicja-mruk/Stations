package com.example.myapp.core.domain.model

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERVER_ERROR,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
        EMPTY_DB
    }
}