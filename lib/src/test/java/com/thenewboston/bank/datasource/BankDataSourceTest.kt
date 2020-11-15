package com.thenewboston.bank.datasource

import com.thenewboston.Config
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig

import io.ktor.utils.io.errors.*

import kotlinx.coroutines.runBlocking

import org.junit.jupiter.api.*

import kotlin.test.assertTrue

class BankDataSourceTest {

    private var networkClient = NetworkClient(
        BankConfig(
            ipAddress = Config.IP_ADDRESS,
            port = Config.PORT,
            protocol = Config.PROTOCOL
        )
    )

    private var bankDataSource = BankDataSource(networkClient)

    @Nested
    @DisplayName("Given a valid request that should return success")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenValidRequests {

        @Test
        fun `test fetch list of available banks`() = runBlocking {
            val response = bankDataSource.fetchBanks()

            check(response is Outcome.Success)
            assertTrue(response.value.banks.isNotEmpty())
        }
    }

    @Nested
    @DisplayName("Given invalid request that should fail")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenInvalidRequest {

        @Test
        fun `should return error outcome for IOException`() = runBlocking {
            networkClient = NetworkClient(
                BankConfig(
                    ipAddress = "",
                    port = Config.PORT,
                    protocol = Config.PROTOCOL
                )
            )

            bankDataSource = BankDataSource(networkClient)
            // when
            val response = bankDataSource.fetchBanks()

            // then
            check(response is Outcome.Error)
            Assertions.assertTrue(response.cause is IOException)
        }
    }
}
