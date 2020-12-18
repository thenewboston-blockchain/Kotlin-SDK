package com.thenewboston.api.bankapi.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.Config
import com.thenewboston.utils.BankApiMockEngine
import com.thenewboston.utils.Mocks
import com.thenewboston.utils.Some
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.string.beEmpty
import io.kotest.matchers.string.contain
import io.kotest.matchers.types.beInstanceOf
import io.ktor.util.*
import io.ktor.utils.io.errors.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle

@KtorExperimentalAPI
@ExperimentalCoroutinesApi
@TestInstance(Lifecycle.PER_CLASS)
class BankDataSourceTest {

    @MockK
    lateinit var networkClient: NetworkClient

    private val mockEngine = BankApiMockEngine()

    private lateinit var bankDataSource: BankDataSource

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)

        bankDataSource = BankDataSource(networkClient)
    }

    @Nested
    @DisplayName("Given successful request...")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GivenSucceedingRequest {

        @Nested
        @DisplayName("When performing a GET request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenGetRequest {

            @BeforeEach
            fun setup() {
                every { networkClient.defaultClient } returns mockEngine.getSuccess()
            }

            @Test
            fun `should fetch list of available banks`() = runBlockingTest {
                val response = bankDataSource.fetchBanks()

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch bank details from config`() = runBlockingTest {
                val response = bankDataSource.fetchBankDetails()

                check(response is Outcome.Success)
                Config.IP_ADDRESS should contain(response.value.ip_address)
            }

            @Test
            fun `should fetch list of available bank transactions`() = runBlockingTest {
                val response = bankDataSource.fetchBankTransactions()

                check(response is Outcome.Success)

                response.value.bankTransactions.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch list of validators successfully`() = runBlockingTest {
                // when
                val body = bankDataSource.fetchValidators()

                // then
                check(body is Outcome.Success)
                body.value.count shouldBeGreaterThan 0
                body.value.results.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch single validator successfully`() = runBlockingTest {
                // given
                val nodeIdentifier =
                    "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"

                // when
                val body = bankDataSource.fetchValidator(nodeIdentifier)

                // then
                check(body is Outcome.Success)
                body.value.nodeIdentifier should contain(nodeIdentifier)
                body.value.ipAddress should contain("127.0.0.1")
            }

            @Test
            fun `should fetch list of accounts successfully`() = runBlockingTest {
                val response = bankDataSource.fetchAccounts()

                check(response is Outcome.Success)
                response.value.count shouldBeGreaterThan 0
                response.value.results.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch list of blocks successfully`() = runBlockingTest {
                val response = bankDataSource.fetchBlocks()

                check(response is Outcome.Success)
                response.value.count shouldBeGreaterThan 0
                response.value.blocks.shouldNotBeEmpty()
            }

            @Test
            fun `test send bank trust successfully`() = runBlockingTest {
                every { networkClient.defaultClient } returns mockEngine.patchSuccess()

                val request = Mocks.trustRequest(42.0)

                val response = bankDataSource.updateBankTrust(request)

                check(response is Outcome.Success)
                response.value.accountNumber shouldBe "dfddf07ec15cbf363ecb52eedd7133b70b3ec896b488460bcecaba63e8e36be5"
                response.value.trust shouldBe request.message.trust
            }

            @Test
            fun `test fetch list of invalid blocks successfully`() = runBlockingTest {
                val response = bankDataSource.fetchInvalidBlocks()

                check(response is Outcome.Success)
                response.value.count shouldBeGreaterThan 0
                response.value.results.shouldNotBeEmpty()
            }
        }

        @Nested
        @DisplayName("When performing POST request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenPostRequest {

            @BeforeEach
            fun given() {
                every { networkClient.defaultClient } returns mockEngine.postSuccess()
            }

            @Test
            fun `should return success with original invalid block identifier`() = runBlockingTest {
                // given
                val request = Mocks.postInvalidBlockRequest()

                // when
                val response = bankDataSource.sendInvalidBlock(request)

                // then
                check(response is Outcome.Success)
                response.value.id shouldNot beEmpty()
                response.value.blockIdentifier shouldBe request.message.blockIdentifier
            }

            @Test
            fun `should return success with balanceKey `() = runBlockingTest {
                // given
                val request = Mocks.postBlockRequest()

                // when
                val response = bankDataSource.sendBlock(request)

                // then
                check(response is Outcome.Success)
                response.value.id shouldNot beEmpty()
                response.value.balanceKey shouldBe request.message.balanceKey
            }
        }

        @Nested
        @DisplayName("When performing PATCH request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenPatchRequest {

            @BeforeEach
            fun given() {
                every { networkClient.defaultClient } returns mockEngine.patchSuccess()
            }

            @Test
            fun `should send bank trust successfully`() = runBlockingTest {
                // given
                val request = Mocks.trustRequest(3.14)

                // when
                val response = bankDataSource.updateBankTrust(request)

                // then
                check(response is Outcome.Success)
                response.value.accountNumber shouldBe "dfddf07ec15cbf363ecb52eedd7133b70b3ec896b488460bcecaba63e8e36be5"
                response.value.trust shouldBe request.message.trust
            }

            @Test
            fun `should update account with given trust level`() = runBlockingTest {
                // given
                val trustRequest = Mocks.trustRequest(17.99)
                val accountNumber = Some.accountNumber

                // when
                val response = bankDataSource.updateAccountTrust(accountNumber, trustRequest)

                // then
                check(response is Outcome.Success)
                response.value.accountNumber shouldBe accountNumber
                response.value.trust shouldBe trustRequest.message.trust
            }
        }
    }

    @Test
    fun `test return error outcome for list of invalid blocks IOException`() = runBlockingTest {
        // when
        val response = bankDataSource.fetchInvalidBlocks()

        // then
        check(response is Outcome.Error)
        response.cause should beInstanceOf<IOException>()
    }

    @Nested
    @DisplayName("Given request that should fail")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GivenFailingRequest {

        @Nested
        @DisplayName("When performing GET request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenGetRequest {

            @BeforeEach
            fun setup() {
                every { networkClient.defaultClient } returns mockEngine.getErrors()
            }

            @Test
            fun `should return error outcome for IOException`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchBanks()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "Failed to retrieve banks"
            }

            @Test
            fun `should return error outcome for bank details IOException`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchBankDetails()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "Failed to retrieve bank details"
            }

            @Test
            fun `should return error outcome for bank transactions IOException`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchBankTransactions()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "Failed to retrieve bank transactions"
            }

            @Test
            fun `should return error outcome for single validator`() = runBlockingTest {
                val nodeIdentifier = "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"
                val response = bankDataSource.fetchValidator(nodeIdentifier)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "Could not fetch validator with NID $nodeIdentifier"
            }

            @Test
            fun `should return error outcome for nonexistent node identifier`() = runBlockingTest {
                every { networkClient.defaultClient } returns mockEngine.getSuccess()

                // given
                val nonExistentNodeIdentifier = "foo"

                // when
                val body = bankDataSource.fetchValidator(nonExistentNodeIdentifier)

                // then
                check(body is Outcome.Error)
                body.cause should beInstanceOf<IOException>()
                body.cause?.message shouldBe "Could not fetch validator with NID $nonExistentNodeIdentifier"
            }

            @Test
            fun `should return error outcome for list of accounts IOException`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchAccounts()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "Could not fetch list of accounts"
            }

            @Test
            fun `should return error outcome for list of blocks IOException`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchBlocks()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "Could not fetch list of blocks"
            }
        }

        @Nested
        @DisplayName("When sending POST request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenPostRequest {

            @Nested
            @DisplayName("Given server error response...")
            @TestInstance(Lifecycle.PER_CLASS)
            inner class GivenServerErrorResponse {

                @BeforeEach
                fun given() {
                    every { networkClient.defaultClient } returns mockEngine.postErrors()
                }

                @Test
                fun `should return error outcome when sending invalid block`() {
                    runBlockingTest {
                        // given
                        val request = Mocks.postInvalidBlockRequest()

                        // when
                        val response = bankDataSource.sendInvalidBlock(request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        response.cause?.message shouldBe "An error occurred while sending invalid block"
                    }
                }

                @Test
                fun `should return error outcome when sending block`() {
                    runBlockingTest {
                        // given
                        val request = Mocks.postBlockRequest()

                        // when
                        val response = bankDataSource.sendBlock(request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        response.cause?.message shouldBe "An error occurred while sending the block"
                    }
                }
            }

            @Nested
            @DisplayName("Given empty or invalid response body...")
            @TestInstance(Lifecycle.PER_CLASS)
            inner class GivenInvalidResponseBody {

                @BeforeEach
                fun given() {
                    every { networkClient.defaultClient } returns mockEngine.postInvalidSuccess()
                }

                @Test
                fun `should return error outcome when receiving invalid response`() = runBlockingTest {
                    // given
                    val request = Mocks.postInvalidBlockRequest()

                    // when
                    val response = bankDataSource.sendInvalidBlock(request)

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe "Received invalid response when sending invalid block with identifier ${request.message.blockIdentifier}"
                }

                @Test
                fun `should return error outcome when receiving invalid response for sending block`() = runBlockingTest {
                    // given
                    val request = Mocks.postBlockRequest()

                    // when
                    val response = bankDataSource.sendBlock(request)

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe "Received invalid response when sending block with balance key: ${request.message.balanceKey}"
                }
            }
        }

        @Nested
        @DisplayName("When sending PATCH request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenPatchRequest {

            @Nested
            @DisplayName("Given server error response...")
            @TestInstance(Lifecycle.PER_CLASS)
            inner class GivenServerErrorResponse {

                @BeforeEach
                fun given() {
                    every { networkClient.defaultClient } returns mockEngine.patchErrors()
                }

                @Test
                fun `should return error outcome for sending bank trust`() {
                    runBlockingTest {
                        // given
                        val request = Mocks.trustRequest()

                        // when
                        val response = bankDataSource.updateBankTrust(request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        response.cause?.message shouldBe "Could not send bank trust for ${request.nodeIdentifier}"
                    }
                }

                @Test
                fun `should return error outcome for sending account trust`() {
                    runBlockingTest {
                        // given
                        val accountNumber = Some.accountNumber
                        val request = Mocks.trustRequest()

                        // when
                        val response = bankDataSource.updateAccountTrust(accountNumber, request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        response.cause?.message shouldBe "Could not update trust level of given account"
                    }
                }
            }

            @Nested
            @DisplayName("Given empty or invalid response body...")
            @TestInstance(Lifecycle.PER_CLASS)
            inner class GivenInvalidResponseBody {

                @BeforeEach
                fun given() {
                    every { networkClient.defaultClient } returns mockEngine.patchEmptySuccess()
                }

                @Test
                fun `should return error outcome for sending bank trust with invalid request`() =
                    runBlockingTest {
                        // given
                        val request = Mocks.trustRequest()

                        // when
                        val response = bankDataSource.updateBankTrust(request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        response.message shouldBe "Received invalid request when updating trust level of bank with" +
                            " ${request.nodeIdentifier}"
                    }

                @Test
                fun `should return error outcome if account trust response is invalid`() =
                    runBlockingTest {
                        // given
                        val request = Mocks.trustRequest()
                        val accountNumber = Some.accountNumber

                        // when
                        val response = bankDataSource.updateAccountTrust(accountNumber, request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        response.message shouldBe "Received unexpected response when updating trust level of account $accountNumber"
                    }
            }
        }
    }
}
