package com.elguille.purrfectpics.presentation

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class TextResource {
    class DynamicString(val string: String) : TextResource()
    class StringResource(@StringRes val id: Int, vararg val args: Any) : TextResource()

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> string
        is StringResource -> stringResource(id, *args)
    }

    fun asString(context: Context): String = when (this) {
        is DynamicString -> string
        is StringResource -> context.getString(id, *args)
    }
}