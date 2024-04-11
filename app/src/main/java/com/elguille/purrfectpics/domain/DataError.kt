package com.elguille.purrfectpics.domain

sealed interface DataError: Error {
    data object Network: DataError
    data object Local: DataError
}