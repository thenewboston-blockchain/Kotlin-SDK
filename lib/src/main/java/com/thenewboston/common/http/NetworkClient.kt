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
import kotlinx.serialization.json.Json

class NetworkClient(
    val bankConfig: BankConfig
) {

    private val nonStrictJson = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    @KtorExperimentalAPI
    val defaultClient: HttpClient by lazy {
        newClient(
            ipAddress = this@NetworkClient.bankConfig.ipAddress,
            port = this@NetworkClient.bankConfig.port
        )
    }

    @KtorExperimentalAPI
    fun newClient(ipAddress: String, port: Int): HttpClient {
        return HttpClient(CIO) {
            defaultRequest {
                this.host = ipAddress
                this.port = port
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer(nonStrictJson)
            }
        }
    }
}
