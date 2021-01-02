package com.thenewboston.utils

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class MockHttpClientManager {

    fun httpClient(callback: (MockEngineConfig) -> Unit): HttpClient {
        return HttpClient(MockEngine) {
            engine {
                callback.invoke(this)
            }

            installJsonFeature()

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

    private fun HttpClientConfig<MockEngineConfig>.installJsonFeature() {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json())
        }
    }

    private fun json(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
}
