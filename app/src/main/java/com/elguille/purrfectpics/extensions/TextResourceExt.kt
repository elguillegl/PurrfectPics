package com.elguille.purrfectpics.extensions

import com.elguille.purrfectpics.R
import com.elguille.purrfectpics.domain.DataError
import com.elguille.purrfectpics.domain.Resource
import com.elguille.purrfectpics.presentation.TextResource

fun DataError.asTextResource(): TextResource = when (this) {
    DataError.Network.TIMEOUT -> {
        TextResource.StringResource(R.string.data_error_timeout)
    }
    DataError.Network.UNKNOWN -> {
        TextResource.StringResource(R.string.data_error_unknown)
    }
    else -> {
        TextResource.StringResource(R.string.error_unknown)
    }
}

fun Resource.Error<*, DataError>.asTextResource(): TextResource = error.asTextResource()