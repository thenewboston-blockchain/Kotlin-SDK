package com.thenewboston.api.bankapi.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.utils.BankApiMockEngine
import com.thenewboston.utils.ErrorMessages
import com.thenewboston.utils.Mocks
import com.thenewboston.utils.PaginationOptions
import com.thenewboston.utils.Some
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.string.beEmpty
import io.kotest.matchers.string.contain
import io.kotest.matchers.string.shouldNotBeEmpty
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
            fun `should fetch list of 20 available banks`() = runBlockingTest {
                val response = bankDataSource.fetchBanks(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20 // offset = 20
                response.value.banks.size shouldBeLessThanOrEqual 20 // limit = 30
            }

            fun `should fetch list of 30 available banks`() = runBlockingTest {
                val response = bankDataSource.fetchBanks(PaginationOptions(0, 30))

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0 // offset = 0
                response.value.banks.size shouldBeLessThanOrEqual 30 // limit = 30
            }

            @Test
            fun `should fetch bank details from config`() = runBlockingTest {
                val response = bankDataSource.fetchBankDetails()

                check(response is Outcome.Success)
                "143.110.137.54" should contain(response.value.ipAddress)
            }

            @Test
            fun `should fetch list of 20 available bank transactions`() = runBlockingTest {
                val response = bankDataSource.fetchBankTransactions(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.bankTransactions.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.bankTransactions.size shouldBeLessThanOrEqual 20
            }

            fun `should fetch list of 30 available bank transactions`() = runBlockingTest {
                val response = bankDataSource.fetchBankTransactions(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.bankTransactions.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.bankTransactions.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch list of 20 validators successfully`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchValidators(PaginationOptions(20, 20))

                // then
                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.results.size shouldBeLessThanOrEqual 20
            }

            fun `should fetch list of 30 validators successfully`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchValidators(PaginationOptions(20, 20))

                // then
                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.results.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch single validator successfully`() = runBlockingTest {
                // given
                val nodeIdentifier =
                    "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"

                // when
                val response = bankDataSource.fetchValidator(nodeIdentifier)

                // then
                check(response is Outcome.Success)
                response.value.nodeIdentifier should contain(nodeIdentifier)
                response.value.ipAddress should contain("127.0.0.1")
            }

            @Test
            fun `should fetch list of 20 accounts successfully`() = runBlockingTest {
                val response = bankDataSource.fetchAccounts(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.results.size shouldBeLessThanOrEqual 20
            }

            fun `should fetch list of 30 accounts successfully`() = runBlockingTest {
                val response = bankDataSource.fetchAccounts(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.results.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch list of 20 blocks successfully`() = runBlockingTest {
                val response = bankDataSource.fetchBlocks(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.blocks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.blocks.size shouldBeLessThanOrEqual 20
            }

            fun `should fetch list of 30 blocks successfully`() = runBlockingTest {
                val response = bankDataSource.fetchBlocks(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.blocks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.blocks.size shouldBeLessThanOrEqual 30
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
            fun `test fetch list of 20 invalid blocks successfully`() = runBlockingTest {
                val response = bankDataSource.fetchInvalidBlocks(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.results.size shouldBeLessThanOrEqual 20
            }

            fun `test fetch list of 30 invalid blocks successfully`() = runBlockingTest {
                val response = bankDataSource.fetchInvalidBlocks(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.results.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `test fetch list of 20 validator confirmation services successfully`() = runBlockingTest {
                val response = bankDataSource.fetchValidatorConfirmationServices(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.services.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.services.size shouldBeLessThanOrEqual 20
            }

            fun `test fetch list of 30 validator confirmation services successfully`() = runBlockingTest {
                val response = bankDataSource.fetchValidatorConfirmationServices(PaginationOptions(20, 20))

                check(response is Outcome.Success)
                response.value.services.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.services.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch clean successfully`() = runBlockingTest {
                val response = bankDataSource.fetchClean()

                check(response is Outcome.Success)
                response.value.cleanStatus.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch crawl successfully`() = runBlockingTest {
                val response = bankDataSource.fetchCrawl()

                check(response is Outcome.Success)
                response.value.crawlStatus.shouldNotBeEmpty()
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
            fun `should send validator confirmation successfully`() = runBlockingTest {
                val request = Mocks.confirmationServiceRequest()

                // when
                val response = bankDataSource.sendValidatorConfirmationServices(request)

                // then
                check(response is Outcome.Success)
                response.value.start shouldBe request.message.start.toString()
                response.value.end shouldBe request.message.end.toString()
            }

            @Test
            fun `should send upgrade notice successfully`() = runBlockingTest {
                val request = Mocks.upgradeNoticeRequest()

                val response = bankDataSource.sendUpgradeNotice(request)

                check(response is Outcome.Success)
                response.value shouldNot beEmpty()
                response.value shouldBe "Successfully sent upgrade notice"
            }

            @Test
            fun `should return success with clean status `() = runBlockingTest {
                // given
                val request = Mocks.postCleanRequest()

                // when
                val response = bankDataSource.sendClean(request)

                // then
                check(response is Outcome.Success)
                response.value.cleanStatus shouldNot beEmpty()
                response.value.cleanStatus shouldBe request.data.clean
            }

            @Test
            fun `should send connection requests successfully`() = runBlockingTest {
                val request = Mocks.connectionRequest()

                val response = bankDataSource.sendConnectionRequests(request)

                check(response is Outcome.Success)
                response.value shouldNot beEmpty()
                response.value shouldBe "Successfully sent connection requests"
            }

            @Test
            fun `should return success with crawl status `() = runBlockingTest {
                // given
                val request = Mocks.postCrawlRequest()

                // when
                val response = bankDataSource.sendCrawl(request)

                // then
                check(response is Outcome.Success)
                response.value.crawlStatus shouldNot beEmpty()
                response.value.crawlStatus shouldBe request.data.crawl
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
                val response = bankDataSource.fetchBanks(PaginationOptions(0, 20))

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
                val response = bankDataSource.fetchBankTransactions(PaginationOptions(0, 20))

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
                val response = bankDataSource.fetchValidator(nonExistentNodeIdentifier)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "Could not fetch validator with NID $nonExistentNodeIdentifier"
            }

            @Test
            fun `should return error outcome for list of accounts IOException`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchAccounts(PaginationOptions(0, 20))

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "Could not fetch list of accounts"
            }

            @Test
            fun `should return error outcome for list of blocks IOException`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchBlocks(PaginationOptions(0, 20))

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "Could not fetch list of blocks"
            }

            @Test
            fun `should return error outcome for list of invalid blocks IOException`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchInvalidBlocks(PaginationOptions(0, 20))

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
            }

            @Test
            fun `should return error outcome for validator confirmation services`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchValidatorConfirmationServices(PaginationOptions(0, 20))

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "An error occurred while fetching validator confirmation services"
            }

            @Test
            fun `should return error outcome for clean process`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchClean()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "Failed to update the network"
            }

            @Test
            fun `should return error outcome for crawling process`() = runBlockingTest {
                // when
                val response = bankDataSource.fetchCrawl()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.cause?.message shouldBe "An error occurred while sending crawl request"
            }

            @Nested
            @DisplayName("Given empty or invalid response body...")
            @TestInstance(Lifecycle.PER_CLASS)
            inner class GivenInvalidResponseBody {

                @BeforeEach
                fun given() {
                    every { networkClient.defaultClient } returns mockEngine.getEmptySuccess()
                }

                @Test
                fun `should return error outcome for empty validator confirmation services`() = runBlockingTest {
                    // when
                    val response = bankDataSource.fetchValidatorConfirmationServices(PaginationOptions(0, 20))

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
                }

                @Test
                fun `should return error outcome for empty banks`() = runBlockingTest {
                    // when
                    val response = bankDataSource.fetchBanks(PaginationOptions(0, 20))

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
                }

                @Test
                fun `should return error outcome for empty accounts`() = runBlockingTest {
                    // when
                    val response = bankDataSource.fetchAccounts(PaginationOptions(0, 20))

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
                }

                @Test
                fun `should return error outcome for empty validators`() = runBlockingTest {
                    // when
                    val response = bankDataSource.fetchValidators(PaginationOptions(0, 20))

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
                }

                @Test
                fun `should return error outcome for empty blocks`() = runBlockingTest {
                    // when
                    val response = bankDataSource.fetchBlocks(PaginationOptions(0, 20))

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
                }

                @Test
                fun `should return error outcome for empty invalid blocks`() = runBlockingTest {
                    // when
                    val response = bankDataSource.fetchInvalidBlocks(PaginationOptions(0, 20))

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe "No invalid blocks are available at this time"
                }

                @Test
                fun `should return error outcome for empty bank transactions`() = runBlockingTest {
                    // when
                    val response = bankDataSource.fetchBankTransactions(PaginationOptions(0, 20))

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe "Error bank transactions"
                }

                @Test
                fun `should return error outcome for empty clean process`() = runBlockingTest {
                    // when
                    val response = bankDataSource.fetchClean()

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe "The network clean process is not successful"
                }

                @Test
                fun `should return error outcome for empty crawling process`() = runBlockingTest {
                    // when
                    val response = bankDataSource.fetchCrawl()

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe "The network crawling process is not successful"
                }
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
                fun `should return error outcome for sending validator confirmation services`() = runBlockingTest {
                    // given
                    val request = Mocks.confirmationServiceRequest()

                    // when
                    val response = bankDataSource.sendValidatorConfirmationServices(request)

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.cause?.message shouldBe "An error occurred while sending validator confirmation services"
                }

                @Test
                fun `should return error outcome for sending upgrade notice`() = runBlockingTest {
                    // given
                    val request = Mocks.upgradeNoticeRequest()

                    // when
                    val response = bankDataSource.sendUpgradeNotice(request)

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.cause?.message shouldBe "An error occurred while sending upgrade notice"
                }

                @Test
                fun `should return error outcome when sending clean`() {
                    runBlockingTest {
                        // given
                        val request = Mocks.postCleanRequest()

                        // when
                        val response = bankDataSource.sendClean(request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        response.cause?.message shouldBe "An error occurred while sending the clean request"
                    }
                }

                @Test
                fun `should return error outcome for sending connection requests`() = runBlockingTest {
                    // given
                    val request = Mocks.connectionRequest()

                    // when
                    val response = bankDataSource.sendConnectionRequests(request)

                    // then
                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.cause?.message shouldBe "An error occurred while sending connection requests"
                }

                @Test
                fun `should return error outcome when sending crawl`() {
                    runBlockingTest {
                        // given
                        val request = Mocks.postCrawlRequest()

                        // when
                        val response = bankDataSource.sendCrawl(request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        response.cause?.message shouldBe "An error occurred while sending the crawl request"
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
                fun `should return error outcome for sending invalid request for confirmation services`() =
                    runBlockingTest {
                        // given
                        val request = Mocks.confirmationServiceRequest()

                        // when
                        val response = bankDataSource.sendValidatorConfirmationServices(request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        val nodeIdentifier = request.nodeIdentifier
                        val message =
                            "Received invalid response sending confirmation services with node identifier: $nodeIdentifier"
                        response.message shouldBe message
                    }

                @Test
                fun `should return error outcome when receiving invalid response for sending clean`() =
                    runBlockingTest {
                        // given
                        val request = Mocks.postCleanRequest()

                        // when
                        val response = bankDataSource.sendClean(request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        response.message shouldBe "Received invalid response when sending block with clean: ${request.data.clean}"
                    }

                @Test
                fun `should return error outcome when receiving invalid response for sending crawl`() =
                    runBlockingTest {
                        // given
                        val request = Mocks.postCrawlRequest()

                        // when
                        val response = bankDataSource.sendCrawl(request)

                        // then
                        check(response is Outcome.Error)
                        response.cause should beInstanceOf<IOException>()
                        response.message shouldBe "Received invalid response when sending block with crawl: ${request.data.crawl}"
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
                fun `should return error outcome when receiving invalid response for sending block`() =
                    runBlockingTest {
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
    }
}
