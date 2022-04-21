package com.godamy.myplayer.framework.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.godamy.myplayer.domain.Error
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toError(): Error = when (this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "Unknown")
}

@Suppress("TooGenericExceptionCaught")
inline fun <T> tryCall(action: () -> T): Either<Error, T> = try {
    action().right()
} catch (e: Exception) {
    e.toError().left()
}
