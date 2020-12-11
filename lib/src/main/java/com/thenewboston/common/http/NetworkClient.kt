package com.thenewboston.common.http

import io.ktor.client.HttpClient
import io.ktor.util.KtorExperimentalAPI

class NetworkClient(
    private val client: HttpClient
) {

    @KtorExperimentalAPI
    val defaultClient: HttpClient by lazy {
        client
    }
}
