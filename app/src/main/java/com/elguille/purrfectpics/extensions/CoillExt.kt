package com.elguille.purrfectpics.extensions

import coil.request.ErrorResult
import com.elguille.purrfectpics.domain.DataError
import java.net.SocketTimeoutException

fun ErrorResult.toDataError(): DataError {
    return when (this.throwable) {
        is SocketTimeoutException -> DataError.Network.TIMEOUT
        else -> DataError.Network.UNKNOWN
    }
}