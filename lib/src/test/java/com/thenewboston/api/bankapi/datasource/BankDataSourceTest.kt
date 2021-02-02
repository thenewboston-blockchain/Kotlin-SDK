package com.thenewboston.api.bankapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PatchDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.Outcome
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
import io.mockk.coEvery
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
    lateinit var getDataSource: GetDataSource

    @MockK
    lateinit var postDataSource: PostDataSource

    @MockK
    lateinit var patchDataSource: PatchDataSource

    private lateinit var bankDataSource: BankDataSource

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)

        bankDataSource = BankDataSource(getDataSource, patchDataSource, postDataSource)
    }

    @Nested
    @DisplayName("Given successful request...")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GivenSucceedingRequest {

        @Nested
        @DisplayName("When performing a GET request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenGetRequest {

            private val paginationTwenty = Mocks.paginationOptionsTwenty()
            private val paginationThirty = Mocks.paginationOptionsThirty()

            @Test
            fun `should fetch list of 20 available banks`() = runBlockingTest {
                val value = Mocks.banks(paginationTwenty)
                coEvery { getDataSource.banks(paginationTwenty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchBanks(paginationTwenty)

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20 // offset = 20
                response.value.banks.size shouldBeLessThanOrEqual 20 // limit = 30
            }

            @Test
            fun `should fetch list of 30 available banks`() = runBlockingTest {
                val value = Mocks.banks(paginationThirty)
                coEvery { getDataSource.banks(paginationThirty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchBanks(paginationThirty)

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0 // offset = 0
                response.value.banks.size shouldBeLessThanOrEqual 30 // limit = 30
            }

            @Test
            fun `should fetch bank details from config`() = runBlockingTest {
                coEvery { getDataSource.bankDetail() } returns Outcome.Success(Mocks.bankDetails())

                val response = bankDataSource.fetchBankDetails()

                check(response is Outcome.Success)
                "143.110.137.54" should contain(response.value.ipAddress)
            }

            @Test
            fun `should fetch list of 20 available bank transactions`() = runBlockingTest {
                val value = Mocks.bankTransactions(paginationTwenty)
                coEvery { getDataSource.bankTransactions(paginationTwenty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchBankTransactions(paginationTwenty)

                check(response is Outcome.Success)
                response.value.bankTransactions.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.bankTransactions.size shouldBeLessThanOrEqual 20
            }

            @Test
            fun `should fetch list of 30 available bank transactions`() = runBlockingTest {
                val value = Mocks.bankTransactions(paginationTwenty)
                coEvery { getDataSource.bankTransactions(paginationThirty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchBankTransactions(paginationThirty)

                check(response is Outcome.Success)
                response.value.bankTransactions.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.bankTransactions.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch list of 20 validators successfully`() = runBlockingTest {
                val value = Mocks.validators(paginationTwenty)
                coEvery { getDataSource.validators(paginationTwenty) } returns Outcome.Success(value)
                // when
                val response = bankDataSource.fetchValidators(paginationTwenty)

                // then
                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.results.size shouldBeLessThanOrEqual 20
            }

            @Test
            fun `should fetch list of 30 validators successfully`() = runBlockingTest {
                val value = Mocks.validators(paginationThirty)
                coEvery { getDataSource.validators(paginationThirty) } returns Outcome.Success(value)

                // when
                val response = bankDataSource.fetchValidators(paginationThirty)

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

                coEvery { getDataSource.validator(nodeIdentifier) } returns Outcome.Success(Mocks.validator())

                // when
                val response = bankDataSource.fetchValidator(nodeIdentifier)

                // then
                check(response is Outcome.Success)
                response.value.nodeIdentifier should contain(nodeIdentifier)
                response.value.ipAddress should contain("127.0.0.1")
            }

            @Test
            fun `should fetch list of 20 accounts successfully`() = runBlockingTest {
                val value = Mocks.accounts(paginationTwenty)
                coEvery { getDataSource.accounts(paginationTwenty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchAccounts(paginationTwenty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.results.size shouldBeLessThanOrEqual 20
            }

            @Test
            fun `should fetch list of 30 accounts successfully`() = runBlockingTest {
                val value = Mocks.accounts(paginationThirty)
                coEvery { getDataSource.accounts(paginationThirty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchAccounts(paginationThirty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.results.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch list of 20 blocks successfully`() = runBlockingTest {
                val value = Mocks.blocks(paginationTwenty)
                coEvery { getDataSource.blocks(paginationTwenty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchBlocks(paginationTwenty)

                check(response is Outcome.Success)
                response.value.blocks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.blocks.size shouldBeLessThanOrEqual 20
            }

            @Test
            fun `should fetch list of 30 blocks successfully`() = runBlockingTest {
                val value = Mocks.blocks(paginationThirty)
                coEvery { getDataSource.blocks(paginationThirty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchBlocks(paginationThirty)

                check(response is Outcome.Success)
                response.value.blocks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.blocks.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `test fetch list of 20 invalid blocks successfully`() = runBlockingTest {
                val value = Mocks.invalidBlocks(paginationTwenty)
                coEvery { getDataSource.invalidBlocks(paginationTwenty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchInvalidBlocks(paginationTwenty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.results.size shouldBeLessThanOrEqual 20
            }

            @Test
            fun `test fetch list of 30 invalid blocks successfully`() = runBlockingTest {
                val value = Mocks.invalidBlocks(paginationThirty)
                coEvery { getDataSource.invalidBlocks(paginationThirty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchInvalidBlocks(paginationThirty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.results.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `test fetch list of 20 validator confirmation services successfully`() = runBlockingTest {
                val value = Mocks.confirmationServicesList(paginationTwenty)
                coEvery { getDataSource.validatorConfirmationServices(paginationTwenty) } returns Outcome.Success(value)

                val response = bankDataSource.fetchValidatorConfirmationServices(paginationTwenty)

                check(response is Outcome.Success)
                response.value.services.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.services.size shouldBeLessThanOrEqual 20
            }

            @Test
            fun `test fetch list of 30 validator confirmation services successfully`() = runBlockingTest {
                coEvery { getDataSource.validatorConfirmationServices(paginationThirty) } returns Outcome.Success(Mocks.confirmationServicesList(paginationThirty))

                val response = bankDataSource.fetchValidatorConfirmationServices(paginationThirty)

                check(response is Outcome.Success)
                response.value.services.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.services.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch clean successfully`() = runBlockingTest {
                coEvery { getDataSource.clean() } returns Outcome.Success(Mocks.cleanSuccess())

                val response = bankDataSource.fetchClean()

                check(response is Outcome.Success)
                response.value.cleanStatus.shouldNotBeEmpty()
            }

            @Test
            fun `should fetch crawl successfully`() = runBlockingTest {
                coEvery { getDataSource.crawl() } returns Outcome.Success(Mocks.crawlSuccess())

                val response = bankDataSource.fetchCrawl()

                check(response is Outcome.Success)
                response.value.crawlStatus.shouldNotBeEmpty()
            }
        }

        @Nested
        @DisplayName("When performing POST request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenPostRequest {

            @Test
            fun `should send validator confirmation successfully`() = runBlockingTest {
                val request = Mocks.confirmationServiceRequest()
                val value = Mocks.confirmationServiceWithMessage(request.message)
                coEvery { postDataSource.doSendConfirmationServices(request) } returns Outcome.Success(value)

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
                val message = "Successfully sent upgrade notice"
                coEvery { postDataSource.doSendUpgradeNotice(request) } returns Outcome.Success(message)

                val response = bankDataSource.sendUpgradeNotice(request)

                check(response is Outcome.Success)
                response.value shouldNot beEmpty()
                response.value shouldBe message
            }

            @Test
            fun `should return success with clean status `() = runBlockingTest {
                // given
                val request = Mocks.postCleanRequest()
                val value = Mocks.postClean(request.data.clean)
                coEvery { postDataSource.doSendClean(request) } returns Outcome.Success(value)

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
                val message = "Successfully sent connection requests"
                coEvery { postDataSource.doSendConnectionRequests(request) } returns Outcome.Success(message)

                val response = bankDataSource.sendConnectionRequests(request)

                check(response is Outcome.Success)
                response.value shouldNot beEmpty()
                response.value shouldBe message
            }

            @Test
            fun `should return success with crawl status `() = runBlockingTest {
                // given
                val request = Mocks.postCrawlRequest()
                val value = Mocks.postCrawl(request.data.crawl)
                coEvery { postDataSource.doSendCrawl(request) } returns Outcome.Success(value)

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

            @Test
            fun `should send bank trust successfully`() = runBlockingTest {
                // given
                val request = Mocks.trustRequest(3.14)

                val value = Mocks.bank(request.message.trust)
                coEvery { patchDataSource.doUpdateBankTrust(request) } returns Outcome.Success(value)

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
                val value = Mocks.account(trustRequest.message.trust)
                coEvery { patchDataSource.doUpdateAccount(accountNumber, trustRequest) } returns Outcome.Success(value)

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

                val value = Mocks.invalidBlock(request.message.blockIdentifier)
                coEvery { patchDataSource.doSendInvalidBlock(request) } returns Outcome.Success(value)

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

                val value = Mocks.postBlock(request.message.balanceKey)
                coEvery { patchDataSource.doSendBlock(request) } returns Outcome.Success(value)

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

            private val pagination = Mocks.paginationOptionsDefault()

            @Test
            fun `should return error outcome for banks IOException`() = runBlockingTest {
                val message = "Failed to retrieve banks"
                coEvery { getDataSource.banks(pagination) } returns Outcome.Error(message, IOException())
                val response = bankDataSource.fetchBanks(pagination)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe "Failed to retrieve banks"
            }

            @Test
            fun `should return error outcome for bank details IOException`() = runBlockingTest {

                val message = "Failed to retrieve bank details"
                coEvery { getDataSource.bankDetail() } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.fetchBankDetails()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for bank transactions IOException`() = runBlockingTest {
                val message = "Failed to retrieve bank transactions"
                coEvery { getDataSource.bankTransactions(pagination) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.fetchBankTransactions(pagination)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe "Failed to retrieve bank transactions"
            }

            @Test
            fun `should return error outcome for single validator`() = runBlockingTest {
                val nodeIdentifier = "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"
                val message = "Could not fetch validator with NID $nodeIdentifier"

                coEvery { getDataSource.validator(nodeIdentifier) } returns Outcome.Error(message, IOException())

                val response = bankDataSource.fetchValidator(nodeIdentifier)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for nonexistent node identifier`() = runBlockingTest {
                // given
                val nonExistentNodeIdentifier = "foo"
                val message = "Could not fetch validator with NID $nonExistentNodeIdentifier"

                coEvery { getDataSource.validator("foo") } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.fetchValidator(nonExistentNodeIdentifier)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for list of accounts IOException`() = runBlockingTest {
                val message = "Could not fetch list of accounts"
                coEvery { getDataSource.accounts(pagination) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.fetchAccounts(pagination)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for list of blocks IOException`() = runBlockingTest {
                val message = "Could not fetch list of blocks"
                coEvery { getDataSource.blocks(pagination) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.fetchBlocks(PaginationOptions(0, 20))

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for list of invalid blocks IOException`() = runBlockingTest {
                val message = "Could not fetch list of invalid blocks"
                coEvery { getDataSource.invalidBlocks(pagination) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.fetchInvalidBlocks(pagination)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for validator confirmation services`() = runBlockingTest {
                val message = "An error occurred while fetching validator confirmation services"
                coEvery { getDataSource.validatorConfirmationServices(pagination) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.fetchValidatorConfirmationServices(pagination)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for clean process`() = runBlockingTest {
                val message = "Failed to update the network"
                coEvery { getDataSource.clean() } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.fetchClean()

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
                val response = bankDataSource.fetchCrawl()

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }
        }

        @Nested
        @DisplayName("When sending POST request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenPostRequest {

            @Test
            fun `should return error outcome for sending validator confirmation services`() = runBlockingTest {
                // given
                val request = Mocks.confirmationServiceRequest()
                val message = "An error occurred while sending validator confirmation services"
                coEvery { postDataSource.doSendConfirmationServices(request) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.sendValidatorConfirmationServices(request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for sending upgrade notice`() = runBlockingTest {
                // given
                val request = Mocks.upgradeNoticeRequest()
                val message = "An error occurred while sending upgrade notice"
                coEvery { postDataSource.doSendUpgradeNotice(request) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.sendUpgradeNotice(request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome when sending clean`() = runBlockingTest {
                // given
                val request = Mocks.postCleanRequest()
                val message = "An error occurred while sending the clean request"
                coEvery { postDataSource.doSendClean(request) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.sendClean(request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for sending connection requests`() = runBlockingTest {
                // given
                val request = Mocks.connectionRequest()
                val message = "An error occurred while sending connection requests"
                coEvery { postDataSource.doSendConnectionRequests(request) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.sendConnectionRequests(request)

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
                val response = bankDataSource.sendCrawl(request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }
        }

        @Nested
        @DisplayName("When sending PATCH request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenPatchRequest {

            @Test
            fun `should return error outcome for sending bank trust`() = runBlockingTest {
                // given
                val request = Mocks.trustRequest()
                val message = "Could not send bank trust for ${request.nodeIdentifier}"
                coEvery { patchDataSource.doUpdateBankTrust(request) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.updateBankTrust(request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for sending account trust`() = runBlockingTest {
                // given
                val accountNumber = Some.accountNumber
                val request = Mocks.trustRequest()
                val message = "Could not update trust level of given account"
                coEvery { patchDataSource.doUpdateAccount(accountNumber, request) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.updateAccountTrust(accountNumber, request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome when sending invalid block`() = runBlockingTest {
                // given
                val request = Mocks.postInvalidBlockRequest()
                val message = "An error occurred while sending invalid block"
                coEvery { patchDataSource.doSendInvalidBlock(request) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.sendInvalidBlock(request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome when sending block`() = runBlockingTest {
                // given
                val request = Mocks.postBlockRequest()
                val message = "An error occurred while sending the block"
                coEvery { patchDataSource.doSendBlock(request) } returns Outcome.Error(message, IOException())

                // when
                val response = bankDataSource.sendBlock(request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }
        }
    }
}
