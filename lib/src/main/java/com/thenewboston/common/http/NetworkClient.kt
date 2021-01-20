package com.thenewboston.common.http

import io.ktor.client.*
import io.ktor.util.*
import javax.inject.Inject

class NetworkClient @Inject constructor(
    private val client: HttpClient
) {

    @KtorExperimentalAPI
    val defaultClient: HttpClient by lazy {
        client
    }
}
