package com.elguille.purrfectpics.extensions

import com.elguille.purrfectpics.domain.DataError
import java.lang.Exception
import java.net.SocketTimeoutException

fun Exception.toDataError(): DataError {
    return when(this) {
        is SocketTimeoutException -> DataError.Network.TIMEOUT
        else -> DataError.Network.UNKNOWN
    }
}