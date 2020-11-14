package com.thenewboston.common.http

import com.thenewboston.Config
import com.thenewboston.common.http.config.BankConfig
import io.ktor.http.HttpStatusCode
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

// This is an integration test to make sure that basic requests work as expected
class HttpServiceTest {
    private val networkClient = NetworkClient(
        BankConfig(
            ipAddress = Config.IP_ADDRESS,
            port = Config.PORT,
            protocol = Config.PROTOCOL
        )
    )

    @KtorExperimentalAPI
    private val httpService = HttpService(networkClient)

    @KtorExperimentalAPI
    @Test
    fun `calls bank config endpoint as expected`() {
        runBlocking {
            val response = httpService.doGet("/config")

            assertEquals(HttpStatusCode.OK, response.status)
        }
    }
}
