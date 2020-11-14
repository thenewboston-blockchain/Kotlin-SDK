package com.thenewboston.common.http

import io.ktor.utils.io.errors.IOException

suspend fun <T : Any> makeApiCall(
    call: suspend () -> Outcome<T>,
    errorMessage: String
): Outcome<T> {
    return try {
        call()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        Outcome.Error(e.message ?: "An error occurred", IOException(errorMessage, e))
    }
}
