package com.elguille.purrfectpics.domain

sealed interface DataError: Error {
    enum class Network: DataError {
        TIMEOUT,
        UNKNOWN
    }
    data object Local: DataError
}