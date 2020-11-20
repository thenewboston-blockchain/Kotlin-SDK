package com.thenewboston.account.datasource

import com.thenewboston.Config
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import io.ktor.util.KtorExperimentalAPI
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@KtorExperimentalAPI
class AccountDataSourceTest {
    private var networkClient = NetworkClient(
        BankConfig(
            ipAddress = Config.IP_ADDRESS,
            port = Config.PORT,
            protocol = Config.PROTOCOL
        )
    )

    private var accountDataSource = AccountDataSource(networkClient)

    @Nested
    @DisplayName("Given a valid request that should return success")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenValidRequests {

        @Test
        fun `test fetch list of accounts`() = runBlocking {
            val response = accountDataSource.fetchAccounts()

            check(response is Outcome.Success)
            assertTrue(response.value.count > 0)
            assertTrue(response.value.results.isNotEmpty())
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

            accountDataSource = AccountDataSource(networkClient)
            // when
            val response = accountDataSource.fetchAccounts()

            // then
            check(response is Outcome.Error)
            assertTrue(response.cause is IOException)
        }
    }
}
