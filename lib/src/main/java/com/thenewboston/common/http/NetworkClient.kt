package com.thenewboston.common.http

import io.ktor.client.*
import io.ktor.util.*

class NetworkClient(
    private val client: HttpClient
) {

    @KtorExperimentalAPI
    val defaultClient: HttpClient by lazy {
        client
    }
}
