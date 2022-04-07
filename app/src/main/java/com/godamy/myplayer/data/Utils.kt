package com.godamy.myplayer.data

import com.godamy.myplayer.domain.Error
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toError(): Error = when (this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "Unknown")
}

@Suppress("TooGenericExceptionCaught")
inline fun <T> tryCall(action: () -> T): Error? = try {
    action()
    null
} catch (e: Exception) {
    e.toError()
}
