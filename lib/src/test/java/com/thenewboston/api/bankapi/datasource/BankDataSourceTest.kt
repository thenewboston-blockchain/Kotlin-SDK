package com.thenewboston.api.bankapi.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import com.thenewboston.common.http.config.Config
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import kotlin.test.assertEquals
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

        @Test
        fun `test fetch bank details from config`() = runBlocking {
            val response = bankDataSource.fetchBankDetails(
                BankConfig(Config.IP_ADDRESS, Config.PORT, Config.PROTOCOL)
            )

            check(response is Outcome.Success)
            assertEquals(Config.IP_ADDRESS, response.value.ip_address)
        }

        @Test
        fun `test fetch list of available bank transactions`() = runBlocking {
            val response = bankDataSource.fetchBankTransactions()

            check(response is Outcome.Success)
            assertTrue(response.value.bankTransactions.isNotEmpty())
        }

        @Test
        fun `test fetch list of validators successfully`() = runBlocking {
            // when
            val body = bankDataSource.fetchValidators()

            // then
            check(body is Outcome.Success)
            Assertions.assertTrue(body.value.count > 0)
            Assertions.assertTrue(body.value.results.isNotEmpty())
        }

        @Test
        fun `test fetch single validator successfully`() = runBlocking {
            // given
            val nodeIdentifier = "2262026a562b0274163158e92e8fbc4d28e519bc5ba8c1cf403703292be84a51"

            // when
            val body = bankDataSource.fetchValidator(nodeIdentifier)

            // then
            check(body is Outcome.Success)
            Assertions.assertEquals(nodeIdentifier, body.value.nodeIdentifier)
            Assertions.assertEquals("54.183.17.224", body.value.ipAddress)
        }

        @Test
        fun `test fetch list of accounts successfully`() = runBlocking {
            val response = bankDataSource.fetchAccounts()

            check(response is Outcome.Success)
            Assertions.assertTrue(response.value.count > 0)
            Assertions.assertTrue(response.value.results.isNotEmpty())
        }
    }

    @Nested
    @DisplayName("Given invalid request that should fail")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenInvalidRequest {

        @Test
        fun `test return error outcome for IOException`() = runBlocking {
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

        @Test
        fun `test return error outcome for bank details IOException`() = runBlocking {
            networkClient = NetworkClient(BankConfig())

            bankDataSource = BankDataSource(networkClient)
            // when
            val response = bankDataSource.fetchBankDetails(BankConfig(ipAddress = ""))

            // then
            check(response is Outcome.Error)
            Assertions.assertTrue(response.cause is IOException)
        }

        @Test
        fun `test return error outcome for bank transactions IOException`() = runBlocking {
            networkClient = NetworkClient(
                BankConfig(
                    ipAddress = "",
                    port = Config.PORT,
                    protocol = Config.PROTOCOL
                )
            )

            bankDataSource = BankDataSource(networkClient)
            // when
            val response = bankDataSource.fetchBankTransactions()

            // then
            check(response is Outcome.Error)
            Assertions.assertTrue(response.cause is IOException)
        }

        @Test
        fun `test return error outcome for nonexistent node identifier`() = runBlocking {
            // given
            val nodeIdentifier = "foo"

            // when
            val body = bankDataSource.fetchValidator(nodeIdentifier)

            // then
            check(body is Outcome.Error)
            Assertions.assertTrue(body.cause is IOException)
        }

        @Test
        fun `test return error outcome for list of accounts IOException`() = runBlocking {
            networkClient = NetworkClient(
                BankConfig(
                    ipAddress = "",
                    port = Config.PORT,
                    protocol = Config.PROTOCOL
                )
            )

            bankDataSource = BankDataSource(networkClient)
            // when
            val response = bankDataSource.fetchAccounts()

            // then
            check(response is Outcome.Error)
            Assertions.assertTrue(response.cause is IOException)
        }
    }
}
