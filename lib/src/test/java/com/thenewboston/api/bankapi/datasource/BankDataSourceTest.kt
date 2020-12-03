package com.thenewboston.api.bankapi.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import com.thenewboston.common.http.config.Config
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.string.contain
import io.kotest.matchers.types.beInstanceOf
import io.ktor.util.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@KtorExperimentalAPI
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
            response.value.banks.shouldNotBeEmpty()
        }

        @Test
        fun `test fetch bank details from config`() = runBlocking {
            val response = bankDataSource.fetchBankDetails(
                BankConfig(Config.IP_ADDRESS, Config.PORT, Config.PROTOCOL)
            )

            check(response is Outcome.Success)
            Config.IP_ADDRESS should contain(response.value.ip_address)
        }

        @Test
        fun `test fetch list of available bank transactions`() = runBlocking {
            val response = bankDataSource.fetchBankTransactions()

            check(response is Outcome.Success)

            response.value.bankTransactions.shouldNotBeEmpty()
        }

        @Test
        fun `test fetch list of validators successfully`() = runBlocking {
            // when
            val body = bankDataSource.fetchValidators()

            // then
            check(body is Outcome.Success)
            body.value.count shouldBeGreaterThan 0
            body.value.results.shouldNotBeEmpty()
        }

        @Test
        fun `test fetch single validator successfully`() = runBlocking {
            // given
            val nodeIdentifier = "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"

            // when
            val body = bankDataSource.fetchValidator(nodeIdentifier)

            // then
            check(body is Outcome.Success)
            body.value.nodeIdentifier should contain(nodeIdentifier)
            body.value.ipAddress should contain("167.71.3.52")
        }

        @Test
        fun `test fetch list of accounts successfully`() = runBlocking {
            val response = bankDataSource.fetchAccounts()

            check(response is Outcome.Success)
            response.value.count shouldBeGreaterThan 0
            response.value.results.shouldNotBeEmpty()
        }

        @Test
        fun `test fetch list of blocks successfully`() = runBlocking {
            val response = bankDataSource.fetchBlocks()

            check(response is Outcome.Success)
            response.value.count shouldBeGreaterThan 0
            response.value.results.shouldNotBeEmpty()
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
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `test return error outcome for bank details IOException`() = runBlocking {
            networkClient = NetworkClient(BankConfig())

            bankDataSource = BankDataSource(networkClient)
            // when
            val response = bankDataSource.fetchBankDetails(BankConfig(ipAddress = ""))

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
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
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `test return error outcome for nonexistent node identifier`() = runBlocking {
            // given
            val nodeIdentifier = "foo"

            // when
            val body = bankDataSource.fetchValidator(nodeIdentifier)

            // then
            check(body is Outcome.Error)
            body.cause should beInstanceOf<IOException>()
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
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `test return error outcome for lis of blocks IOException`() = runBlocking {
            networkClient = NetworkClient(BankConfig(ipAddress = ""))

            bankDataSource = BankDataSource(networkClient)
            // when
            val response = bankDataSource.fetchBlocks()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }
    }
}
