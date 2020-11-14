package com.thenewboston.common.http

import com.thenewboston.common.http.config.BankConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.host
import io.ktor.client.request.port
import io.ktor.util.KtorExperimentalAPI

internal class NetworkClient(private val bankConfig: BankConfig) {

    @KtorExperimentalAPI
    val client: HttpClient by lazy {
        HttpClient(CIO) {
            defaultRequest {
                host = this@NetworkClient.bankConfig.ipAddress
                port = this@NetworkClient.bankConfig.port?.toInt() ?: 80
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}
