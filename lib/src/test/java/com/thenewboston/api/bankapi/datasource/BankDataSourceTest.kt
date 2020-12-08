package com.thenewboston.api.bankapi.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.Config
import com.thenewboston.utils.testBlocking
import com.thenewboston.utils.BankApiMockEngine
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
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.*

@KtorExperimentalAPI
class BankDataSourceTest {

    @MockK
    lateinit var networkClient: NetworkClient

    private val mockEngine = BankApiMockEngine()

    private lateinit var bankDataSource: BankDataSource

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)

        bankDataSource = BankDataSource(networkClient)
    }

        @Nested
        @DisplayName("When performing a GET request...")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class WhenGetRequest {

        @BeforeEach
        fun setup() {
            every { networkClient.defaultClient } returns mockEngine.getSuccess()
        }

        @Test
        fun `test fetch list of available banks`() = runBlockingTest {
            val response = bankDataSource.fetchBanks()

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
            }

        @Test
        fun `test fetch bank details from config`() = runBlockingTest {
            val response = bankDataSource.fetchBankDetails()

                check(response is Outcome.Success)
                Config.IP_ADDRESS should contain(response.value.ip_address)
            }

        @Test
        fun `test fetch list of available bank transactions`() = runBlockingTest {
            val response = bankDataSource.fetchBankTransactions()

                check(response is Outcome.Success)

                response.value.bankTransactions.shouldNotBeEmpty()
            }

        @Test
        fun `test fetch list of validators successfully`() = runBlockingTest {
            // when
            val body = bankDataSource.fetchValidators()

                // then
                check(body is Outcome.Success)
                body.value.count shouldBeGreaterThan 0
                body.value.results.shouldNotBeEmpty()
            }

        @Test
        fun `test fetch single validator successfully`() = runBlockingTest {
            // given
            val nodeIdentifier = "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"

                // when
                val body = validBankDataSource.fetchValidator(nodeIdentifier)

                // then
                check(body is Outcome.Success)
                body.value.nodeIdentifier should contain(nodeIdentifier)
                body.value.ipAddress should contain("127.0.0.1")
            }

        @Test
        fun `test fetch list of accounts successfully`() = runBlockingTest {
            val response = bankDataSource.fetchAccounts()

                check(response is Outcome.Success)
                response.value.count shouldBeGreaterThan 0
                response.value.results.shouldNotBeEmpty()
            }

        @Test
        fun `test fetch list of blocks successfully`() = runBlockingTest {
            val response = bankDataSource.fetchBlocks()

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

        @BeforeEach
        fun setup() {
            every { networkClient.defaultClient } returns mockEngine.getErrors()
        }

        @Test
        fun `test return error outcome for IOException`() = runBlockingTest {
            // when
            val response = invalidBankDataSource.fetchBanks()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `test return error outcome for bank details IOException`() = runBlockingTest {
            // when
            val response = invalidBankDataSource.fetchBankDetails()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `test return error outcome for bank transactions IOException`() = runBlockingTest {
            // when
            val response = invalidBankDataSource.fetchBankTransactions()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `test return error outcome for single validator`() = runBlockingTest {
            val nodeIdentifier = "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"
            val response = bankDataSource.fetchValidator(nodeIdentifier)

            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `test return error outcome for nonexistent node identifier`() = runBlockingTest {
            every { networkClient.defaultClient } returns mockEngine.getSuccess()

            // given
            val nonExistentNodeIdentifier = "foo"

            // when
            val body = validBankDataSource.fetchValidator(nonExistentNodeIdentifier)

            // then
            check(body is Outcome.Error)
            body.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `test return error outcome for list of accounts IOException`() = runBlockingTest {
            // when
            val response = invalidBankDataSource.fetchAccounts()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }

        @Test
        fun `test return error outcome for lis of blocks IOException`() = runBlockingTest {
            // when
            val response = invalidBankDataSource.fetchBlocks()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
        }
    }
}
