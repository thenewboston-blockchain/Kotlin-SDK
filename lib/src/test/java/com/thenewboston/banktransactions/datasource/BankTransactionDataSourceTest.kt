package com.thenewboston.banktransactions.datasource

import com.thenewboston.Config
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig

import io.ktor.utils.io.errors.*

import kotlinx.coroutines.runBlocking

import org.junit.jupiter.api.*

import kotlin.test.assertTrue

class BankTransactionDataSourceTest {

    private var networkClient = NetworkClient(
        BankConfig(
            ipAddress = Config.IP_ADDRESS,
            port = Config.PORT,
            protocol = Config.PROTOCOL
        )
    )

    private var bankTransactionDataSource = BankTransactionsDataSource(networkClient)

    @Nested
    @DisplayName("Given a valid request that should return success")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenValidRequests {

        @Test
        fun `test fetch list of available bank transactions`() = runBlocking {
            val response = bankTransactionDataSource.fetchBankTransactions()

            check(response is Outcome.Success)
            assertTrue(response.value.bankTransactions.isNotEmpty())
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

            bankTransactionDataSource = BankTransactionsDataSource(networkClient)
            // when
            val response = bankTransactionDataSource.fetchBankTransactions()

            // then
            check(response is Outcome.Error)
            Assertions.assertTrue(response.cause is IOException)
        }
    }
}
