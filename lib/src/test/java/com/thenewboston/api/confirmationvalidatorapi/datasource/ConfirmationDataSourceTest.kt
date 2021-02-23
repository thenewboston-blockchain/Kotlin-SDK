package com.thenewboston.api.confirmationvalidatorapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.utils.Mocks
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
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.*

@ExperimentalCoroutinesApi
@KtorExperimentalAPI
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConfirmationDataSourceTest {

    @MockK
    lateinit var getDataSource: GetDataSource

    @MockK
    lateinit var postDataSource: PostDataSource

    @MockK
    lateinit var confirmationDataSource: ConfirmationDataSource

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)

        confirmationDataSource = ConfirmationDataSource(getDataSource, postDataSource)
    }

    @Nested
    @DisplayName("Given successful request...")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenSucceedingRequest {

        private val paginationTwenty = Mocks.paginationOptionsTwenty()
        private val paginationThirty = Mocks.paginationOptionsThirty()

        @Nested
        @DisplayName("When performing a GET request...")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class WhenGetRequest {

            @Test
            fun `should fetch confirmation validator details from config`() = runBlockingTest {
                coEvery {
                    getDataSource.validatorDetails()
                } returns Outcome.Success(Mocks.validatorDetails("CONFIRMATION_VALIDATOR"))

                val response = confirmationDataSource.fetchValidatorDetails()

                check(response is Outcome.Success)
                response.value.nodeType shouldBe "CONFIRMATION_VALIDATOR"
                response.value.rootAccountFile shouldBe "http://20.188.33.93/media/root_account_file.json"
                response.value.ipAddress should contain("172.19.0.13")
            }

            @Test
            fun `should fetch list of 20 accounts successfully from confirmation validator`() = runBlockingTest {
                val value = Mocks.accountsFromValidator(paginationTwenty)
                coEvery { getDataSource.accountsFromValidator(paginationTwenty) } returns Outcome.Success(value)

                val response = confirmationDataSource.fetchAccounts(paginationTwenty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.results.size shouldBeLessThanOrEqual 20
            }

            @Test
            fun `should fetch list of 30 accounts successfully`() = runBlockingTest {
                val value = Mocks.accountsFromValidator(paginationThirty)

                coEvery { getDataSource.accountsFromValidator(paginationThirty) } returns Outcome.Success(value)
                val response = confirmationDataSource.fetchAccounts(paginationThirty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.results.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch single bank successfully`() = runBlockingTest {
                val nodeIdentifier = Some.nodeIdentifier

                coEvery { getDataSource.bankFromValidator(nodeIdentifier) } returns Outcome.Success(Mocks.bankFromValidator())

                val response = getDataSource.bankFromValidator(nodeIdentifier)

                check(response is Outcome.Success)
                response.value.nodeIdentifier should contain(nodeIdentifier)
                response.value.ipAddress shouldBe "127.0.0.1"
            }

            @Test
            fun `should fetch list of 20 available banks sent from validator`() = runBlockingTest {
                val value = Mocks.banksFromValidator(paginationTwenty)
                coEvery { getDataSource.banksFromValidator(paginationTwenty) } returns Outcome.Success(value)

                val response = confirmationDataSource.fetchBanksFromValidator(paginationTwenty)

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20 // offset = 20
                response.value.banks.size shouldBeLessThanOrEqual 20 // limit = 30
            }

            @Test
            fun `should fetch list of 30 available banks sent from validator`() = runBlockingTest {
                val value = Mocks.banksFromValidator(paginationThirty)
                coEvery { getDataSource.banksFromValidator(paginationThirty) } returns Outcome.Success(value)

                val response = confirmationDataSource.fetchBanksFromValidator(paginationThirty)

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0 // offset = 0
                response.value.banks.size shouldBeLessThanOrEqual 30 // limit = 30
            }

            @Test
            fun `should fetch list of 20 validators successfully`() = runBlockingTest {
                val value = Mocks.validators(paginationTwenty)

                coEvery { getDataSource.validators(paginationTwenty) } returns Outcome.Success(value)
                val response = confirmationDataSource.fetchValidators(paginationTwenty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.results.size shouldBeLessThanOrEqual 20
            }

            @Test
            fun `should fetch list of 30 validators successfully`() = runBlockingTest {
                val value = Mocks.validators(paginationThirty)
                coEvery { getDataSource.validators(paginationThirty) } returns Outcome.Success(value)

                val response = confirmationDataSource.fetchValidators(paginationThirty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.results.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch single validator successfully`() = runBlockingTest {
                val nodeIdentifier =
                    "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"

                coEvery { getDataSource.validator(nodeIdentifier) } returns Outcome.Success(Mocks.validator())

                val response = confirmationDataSource.fetchValidator(nodeIdentifier)

                check(response is Outcome.Success)
                response.value.nodeIdentifier should contain(nodeIdentifier)
                response.value.ipAddress shouldBe "127.0.0.1"
            }

            @Test
            fun `should fetch clean successfully`() = runBlockingTest {
                coEvery { getDataSource.clean() } returns Outcome.Success(Mocks.cleanSuccess())

                val response = confirmationDataSource.fetchClean()

                check(response is Outcome.Success)
                response.value.cleanStatus.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch valid confirmations blocks successfully`() = runBlockingTest {
                val blockIdentifier = Some.blockIdentifier

                coEvery {
                    getDataSource.validConfirmationBlocks(blockIdentifier)
                } returns Outcome.Success(Mocks.confirmationBlocks())

                val response = confirmationDataSource.fetchValidConfirmationBlocks(blockIdentifier)

                check(response is Outcome.Success)
                response.value.message.blockIdentifier shouldBe blockIdentifier
            }

            @Test
            fun `should fetch queued confirmation blocks successfully`() = runBlockingTest {
                val blockIdentifier = Some.blockIdentifier

                coEvery {
                    getDataSource.queuedConfirmationBlocks(blockIdentifier)
                } returns Outcome.Success(Mocks.confirmationBlocks())

                val response = confirmationDataSource.fetchQueuedConfirmationBlocks(blockIdentifier)

                check(response is Outcome.Success)
                response.value.message.blockIdentifier shouldBe blockIdentifier
            }

            @Test
            fun `should fetch crawl successfully`() = runBlockingTest {
                coEvery { getDataSource.crawl() } returns Outcome.Success(Mocks.crawlSuccess())

                val response = confirmationDataSource.fetchCrawl()

                check(response is Outcome.Success)
                response.value.crawlStatus.shouldNotBeEmpty()
            }
        }

        @Nested
        @DisplayName("When performing POST request...")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class WhenPostRequest {

            @Test
            fun `should return success with clean status `() = runBlockingTest {
                // given
                val request = Mocks.postCleanRequest()
                val value = Mocks.postClean(request.data.clean)
                coEvery { postDataSource.doSendClean(request) } returns Outcome.Success(value)

                // when
                val response = confirmationDataSource.sendClean(request)

                // then
                check(response is Outcome.Success)
                response.value.cleanStatus shouldNot beEmpty()
                response.value.cleanStatus shouldBe request.data.clean
            }

            @Test
            fun `should return success outcome when sending confirmation blocks `() = runBlockingTest {
                val request = Mocks.confirmationBlocks()

                coEvery {
                    postDataSource.doSendConfirmationBlocks(request)
                } returns Outcome.Success(Mocks.confirmationBlockMessage())

                val response = confirmationDataSource.sendConfirmationBlocks(request)

                check(response is Outcome.Success)
                response.value.blockIdentifier shouldBe Some.blockIdentifier
                response.value.block.message.balanceKey shouldBe Some.balanceKey
            }

            @Test
            fun `should return success with crawl status `() = runBlockingTest {
                // given
                val request = Mocks.postCrawlRequest()
                val value = Mocks.postCrawl(request.data.crawl)
                coEvery { postDataSource.doSendCrawl(request) } returns Outcome.Success(value)

                // when
                val response = confirmationDataSource.sendCrawl(request)

                // then
                check(response is Outcome.Success)
                response.value.crawlStatus shouldNot beEmpty()
                response.value.crawlStatus shouldBe request.data.crawl
            }
        }
    }

    @Nested
    @DisplayName("Given request that should fail")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenFailingRequest {

        private val pagination = Mocks.paginationOptionsDefault()

        @Nested
        @DisplayName("When performing a GET request...")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class WhenGetRequest {

            @Test
            fun `should return error outcome for confirmation validator details IOException`() = runBlockingTest {

                val message = "Failed to retrieve confirmation validator details"
                coEvery { getDataSource.validatorDetails() } returns Outcome.Error(message, IOException())

                val response = confirmationDataSource.fetchValidatorDetails()

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for list of accounts from confirmation validator IOException`() =
                runBlockingTest {
                    val message = "Failed to retrieve accounts from confirmation validator"
                    coEvery { getDataSource.accountsFromValidator(pagination) } returns Outcome.Error(
                        message,
                        IOException()
                    )

                    val response = confirmationDataSource.fetchAccounts(pagination)

                    check(response is Outcome.Error)
                    response.cause should beInstanceOf<IOException>()
                    response.message shouldBe message
                }

            @Test
            fun `should return error outcome for single bank`() = runBlockingTest {
                val nodeIdentifier = Some.nodeIdentifier
                val message = "Failed to retrieve bank from validator"

                coEvery { getDataSource.bankFromValidator(nodeIdentifier) } returns Outcome.Error(
                    message,
                    IOException()
                )

                val response = confirmationDataSource.fetchBankFromValidator(nodeIdentifier)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for banks IOException`() = runBlockingTest {
                val message = "Failed to retrieve banks from validator"
                coEvery { getDataSource.banksFromValidator(pagination) } returns Outcome.Error(message, IOException())
                val response = confirmationDataSource.fetchBanksFromValidator(pagination)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for list of validators IOException`() = runBlockingTest {
                val message = "Could not fetch list of validators"
                coEvery {
                    getDataSource.validators(pagination)
                } returns Outcome.Error(message, IOException())

                val response = confirmationDataSource.fetchValidators(pagination)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for single validator`() = runBlockingTest {
                val nodeIdentifier = "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"
                val message = "Could not fetch validator with NID $nodeIdentifier"

                coEvery {
                    getDataSource.validator(nodeIdentifier)
                } returns Outcome.Error(message, IOException())

                val response = confirmationDataSource.fetchValidator(nodeIdentifier)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for clean process`() = runBlockingTest {
                val message = "Failed to update the network"
                coEvery { getDataSource.clean() } returns Outcome.Error(message, IOException())

                // when
                val response = confirmationDataSource.fetchClean()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for crawling process`() = runBlockingTest {
                val message = "An error occurred while sending crawl request"
                coEvery { getDataSource.crawl() } returns Outcome.Error(message, IOException())
                // when
                val response = confirmationDataSource.fetchCrawl()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for valid confirmation blocks`() = runBlockingTest {
                val blockIdentifier = Some.blockIdentifier
                val message = "Could not fetch valid confirmation blocks with block identifier $blockIdentifier"

                coEvery {
                    getDataSource.validConfirmationBlocks(blockIdentifier)
                } returns Outcome.Error(message, IOException())

                val response = confirmationDataSource.fetchValidConfirmationBlocks(blockIdentifier)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for queued confirmation blocks`() = runBlockingTest {
                val blockIdentifier = Some.blockIdentifier
                val message = "Could not fetch queued confirmation blocks with block identifier $blockIdentifier"

                coEvery {
                    getDataSource.queuedConfirmationBlocks(blockIdentifier)
                } returns Outcome.Error(message, IOException())

                val response = confirmationDataSource.fetchQueuedConfirmationBlocks(blockIdentifier)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }
        }

        @Nested
        @DisplayName("When sending POST request...")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class WhenPostRequest {

            @Test
            fun `should return error outcome when sending clean`() = runBlockingTest {
                // given
                val request = Mocks.postCleanRequest()
                val message = "An error occurred while sending the clean request"
                coEvery { postDataSource.doSendClean(request) } returns Outcome.Error(message, IOException())

                // when
                val response = confirmationDataSource.sendClean(request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome when sending crawl`() = runBlockingTest {
                // given
                val request = Mocks.postCrawlRequest()
                val message = "An error occurred while sending the crawl request"
                coEvery { postDataSource.doSendCrawl(request) } returns Outcome.Error(message, IOException())

                // when
                val response = confirmationDataSource.sendCrawl(request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome when sending confimation blocks`() = runBlockingTest {
                val request = Mocks.confirmationBlocks()
                val message = "An error occurred while sending confirmation blocks"
                coEvery {
                    postDataSource.doSendConfirmationBlocks(request)
                } returns Outcome.Error(message, IOException())

                val response = confirmationDataSource.sendConfirmationBlocks(request)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }
        }
    }
}
