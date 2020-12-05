package com.thenewboston.api.bankapi.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import com.thenewboston.common.http.config.Config
import com.thenewboston.utils.testBlocking
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.contain
import io.kotest.matchers.types.beInstanceOf
import io.ktor.util.*
import io.ktor.utils.io.errors.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@KtorExperimentalAPI
class BankDataSourceTest {

    private var validNetworkClient = NetworkClient(BankConfig())
    private var invalidNetworkClient = NetworkClient(BankConfig(ipAddress = "0.1.2.3"))

    private val validBankDataSource = BankDataSource(validNetworkClient)
    private val invalidBankDataSource = BankDataSource(invalidNetworkClient)

    @Nested
    @DisplayName("Given a valid request...")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenValidRequest {

        @Nested
        @DisplayName("When performing a GET request...")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class WhenGetRequest {

            @Test
            fun `should fetch list of available banks`() = testBlocking {
                val response = validBankDataSource.fetchBanks()

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch bank details from config`() = testBlocking {
                val response = validBankDataSource.fetchBankDetails(
                    BankConfig(Config.IP_ADDRESS, Config.PORT, Config.PROTOCOL)
                )

                check(response is Outcome.Success)
                Config.IP_ADDRESS should contain(response.value.ip_address)
            }

            @Test
            fun `should fetch list of available bank transactions`() = testBlocking {
                val response = validBankDataSource.fetchBankTransactions()

                check(response is Outcome.Success)

                response.value.bankTransactions.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch list of validators successfully`() = testBlocking {
                // when
                val body = validBankDataSource.fetchValidators()

                // then
                check(body is Outcome.Success)
                body.value.count shouldBeGreaterThan 0
                body.value.results.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch single validator successfully`() = testBlocking {
                // given
                val nodeIdentifier =
                    "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"

                // when
                val body = validBankDataSource.fetchValidator(nodeIdentifier)

                // then
                check(body is Outcome.Success)
                body.value.nodeIdentifier should contain(nodeIdentifier)
                body.value.ipAddress should contain("167.71.3.52")
            }

            @Test
            fun `should fetch list of accounts successfully`() = testBlocking {
                val response = validBankDataSource.fetchAccounts()

                check(response is Outcome.Success)
                response.value.count shouldBeGreaterThan 0
                response.value.results.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch list of blocks successfully`() = testBlocking {
                val response = validBankDataSource.fetchBlocks()

                check(response is Outcome.Success)
                response.value.count shouldBeGreaterThan 0
                response.value.results.shouldNotBeEmpty()
            }
        }

        @Nested
        @DisplayName("When performing PATCH request...")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class WhenPatchRequest {

            @Test
            @Disabled("Requires mock HTTP client (#52) to circumvent authorizations") // TODO
            fun `should update account with given trust level`() = testBlocking {
                // given
                val nodeIdentifier = "foo"
                val signature = "foo"
                val accountNumber =
                    "1111111111111111111111111111111111111111111111111111111111111111"
                val newTrustLevel = 50.0

                // when
                val response = validBankDataSource.updateAccount(
                    accountNumber,
                    newTrustLevel,
                    nodeIdentifier,
                    signature
                )

                // then
                check(response is Outcome.Success)
                response.value.accountNumber shouldBe accountNumber
                response.value.trust shouldBe newTrustLevel
            }
        }
    }

    @Nested
    @DisplayName("Given invalid request that should fail")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenInvalidRequest {

        @Test
        fun `should return error outcome for IOException`() = testBlocking {
            // when
            val response = invalidBankDataSource.fetchBanks()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `should return error outcome for bank details IOException`() = testBlocking {
            // when
            val response = invalidBankDataSource.fetchBankDetails(BankConfig(ipAddress = ""))

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `should return error outcome for bank transactions IOException`() = testBlocking {
            // when
            val response = invalidBankDataSource.fetchBankTransactions()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `should return error outcome for nonexistent node identifier`() = testBlocking {
            // given
            val nonExistentNodeIdentifier = "foo"

            // when
            val body = validBankDataSource.fetchValidator(nonExistentNodeIdentifier)

            // then
            check(body is Outcome.Error)
            body.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `should return error outcome for list of accounts IOException`() = testBlocking {
            // when
            val response = invalidBankDataSource.fetchAccounts()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `should return error outcome for lis of blocks IOException`() = testBlocking {
            // when
            val response = invalidBankDataSource.fetchBlocks()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }
    }
}
