package com.thenewboston.api.common

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.utils.ErrorMessages
import com.thenewboston.utils.Mocks
import com.thenewboston.utils.PaginationOptions
import com.thenewboston.utils.Some
import com.thenewboston.utils.mockEngine.bank.BankApiMockEngine
import com.thenewboston.utils.mockEngine.primaryValidator.PrimaryValidatorApiMockEngine
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
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

@KtorExperimentalAPI
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetDataSourceTest {

    @MockK
    lateinit var networkClient: NetworkClient

    private val bankMockEngine = BankApiMockEngine()
    private val primaryMockEngine = PrimaryValidatorApiMockEngine()

    private lateinit var getDataSource: GetDataSource

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)

        getDataSource = GetDataSource(networkClient)
    }

    @Nested
    @DisplayName("Bank: Given successful request")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenSucceedingRequest {

        @BeforeEach
        fun setup() {
            every { networkClient.defaultClient } returns bankMockEngine.getSuccess()
        }

        @Test
        fun `should fetch list of 20 available banks`() = runBlockingTest {
            val response = getDataSource.banks(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.banks.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 20 // offset = 20
            response.value.banks.size shouldBeLessThanOrEqual 20 // limit = 30
        }

        @Test
        fun `should fetch list of 30 available banks`() = runBlockingTest {
            val response = getDataSource.banks(PaginationOptions(0, 30))

            check(response is Outcome.Success)
            response.value.banks.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 0 // offset = 0
            response.value.banks.size shouldBeLessThanOrEqual 30 // limit = 30
        }

        @Test
        fun `should fetch bank details from config`() = runBlockingTest {
            val response = getDataSource.bankDetail()

            check(response is Outcome.Success)
            "143.110.137.54" should contain(response.value.ipAddress)
        }

        @Test
        fun `should fetch list of 20 available bank transactions`() = runBlockingTest {
            val response = getDataSource.bankTransactions(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.bankTransactions.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 20
            response.value.bankTransactions.size shouldBeLessThanOrEqual 20
        }

        @Test
        fun `should fetch list of 30 available bank transactions`() = runBlockingTest {
            val response = getDataSource.bankTransactions(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.bankTransactions.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 0
            response.value.bankTransactions.size shouldBeLessThanOrEqual 30
        }

        @Test
        fun `should fetch list of 20 validators successfully`() = runBlockingTest {
            // when
            val response = getDataSource.validators(PaginationOptions(20, 20))

            // then
            check(response is Outcome.Success)
            response.value.results.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 20
            response.value.results.size shouldBeLessThanOrEqual 20
        }

        @Test
        fun `should fetch list of 30 validators successfully`() = runBlockingTest {
            // when
            val response = getDataSource.validators(PaginationOptions(20, 20))

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
            val body = getDataSource.validator(nodeIdentifier)

            // then
            check(body is Outcome.Success)
            body.value.nodeIdentifier should contain(nodeIdentifier)
            body.value.ipAddress should contain("127.0.0.1")
        }

        @Test
        fun `should fetch list of 20 accounts successfully`() = runBlockingTest {
            val response = getDataSource.accounts(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.results.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 20
            response.value.results.size shouldBeLessThanOrEqual 20
        }

        @Test
        fun `should fetch list of 30 accounts successfully`() = runBlockingTest {
            val response = getDataSource.accounts(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.results.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 0
            response.value.results.size shouldBeLessThanOrEqual 30
        }

        @Test
        fun `should fetch list of 20 blocks successfully`() = runBlockingTest {
            val response = getDataSource.blocks(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.blocks.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 20
            response.value.blocks.size shouldBeLessThanOrEqual 20
        }

        @Test
        fun `should fetch list of 30 blocks successfully`() = runBlockingTest {
            val response = getDataSource.blocks(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.blocks.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 0
            response.value.blocks.size shouldBeLessThanOrEqual 30
        }

        @Test
        fun `test fetch list of 20 invalid blocks successfully`() = runBlockingTest {
            val response = getDataSource.invalidBlocks(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.results.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 20
            response.value.results.size shouldBeLessThanOrEqual 20
        }

        @Test
        fun `test fetch list of 30 invalid blocks successfully`() = runBlockingTest {
            val response = getDataSource.invalidBlocks(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.results.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 0
            response.value.results.size shouldBeLessThanOrEqual 30
        }

        @Test
        fun `test fetch list of 20 validator confirmation services successfully`() = runBlockingTest {
            val response = getDataSource.validatorConfirmationServices(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.services.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 20
            response.value.services.size shouldBeLessThanOrEqual 20
        }

        @Test
        fun `test fetch list of 30 validator confirmation services successfully`() = runBlockingTest {
            val response = getDataSource.validatorConfirmationServices(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.services.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 0
            response.value.services.size shouldBeLessThanOrEqual 30
        }

        @Test
        fun `should fetch clean successfully`() = runBlockingTest {
            val response = getDataSource.clean()

            check(response is Outcome.Success)
            response.value.cleanStatus.shouldNotBeEmpty()
        }

        @Test
        fun `should fetch crawl successfully`() = runBlockingTest {
            val response = getDataSource.crawl()

            check(response is Outcome.Success)
            response.value.crawlStatus.shouldNotBeEmpty()
        }
    }

    @Nested
    @DisplayName("Bank: Given empty or invalid response body")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenInvalidResponseBody {

        @BeforeEach
        fun given() {
            every { networkClient.defaultClient } returns bankMockEngine.getEmptySuccess()
        }

        @Test
        fun `should return error outcome for empty validator confirmation services`() = runBlockingTest {
            // when
            val response = getDataSource.validatorConfirmationServices(Mocks.paginationOptionsDefault())

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
        }

        @Test
        fun `should return error outcome for empty banks`() = runBlockingTest {
            // when
            val response = getDataSource.banks(Mocks.paginationOptionsDefault())

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
        }

        @Test
        fun `should return error outcome for empty accounts`() = runBlockingTest {
            // when
            val response = getDataSource.accounts(Mocks.paginationOptionsDefault())

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
        }

        @Test
        fun `should return error outcome for empty validators`() = runBlockingTest {
            // when
            val response = getDataSource.validators(Mocks.paginationOptionsDefault())

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
        }

        @Test
        fun `should return error outcome for empty blocks`() = runBlockingTest {
            // when
            val response = getDataSource.blocks(Mocks.paginationOptionsDefault())

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
        }

        @Test
        fun `should return error outcome for empty invalid blocks`() = runBlockingTest {
            // when
            val response = getDataSource.invalidBlocks(Mocks.paginationOptionsDefault())

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe "No invalid blocks are available at this time"
        }

        @Test
        fun `should return error outcome for empty bank transactions`() = runBlockingTest {
            // when
            val response = getDataSource.bankTransactions(Mocks.paginationOptionsDefault())

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe "Error bank transactions"
        }

        @Test
        fun `should return error outcome for empty clean process`() = runBlockingTest {
            // when
            val response = getDataSource.clean()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe "The network clean process is not successful"
        }

        @Test
        fun `should return error outcome for empty crawling process`() = runBlockingTest {
            // when
            val response = getDataSource.crawl()

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe "The network crawling process is not successful"
        }
    }

    @Nested
    @DisplayName("Primary Validator: Given successful request")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PrimaryGivenSucceedingRequest {

        private val paginationTwenty = Mocks.paginationOptionsTwenty()
        private val paginationThirty = Mocks.paginationOptionsThirty()

        @BeforeEach
        fun setup() {
            every { networkClient.defaultClient } returns primaryMockEngine.getSuccess()
        }

        @Test
        fun `should fetch single bank successfully`() = runBlockingTest {
            val nodeIdentifier = Some.nodeIdentifier

            val response = getDataSource.bankFromValidator(nodeIdentifier)

            check(response is Outcome.Success)
            response.value.nodeIdentifier should contain(nodeIdentifier)
            response.value.ipAddress should contain("127.0.0.1")
        }

        @Test
        fun `should fetch list of 20 available banks sent from validator`() = runBlockingTest {
            val response = getDataSource.banksFromValidator(paginationTwenty)

            check(response is Outcome.Success)
            response.value.banks.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 20 // offset = 20
            response.value.banks.size shouldBeLessThanOrEqual 20 // limit = 30
        }

        @Test
        fun `should fetch list of 30 available banks sent from validator`() = runBlockingTest {
            val response = getDataSource.banksFromValidator(paginationThirty)

            check(response is Outcome.Success)
            response.value.banks.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 0 // offset = 0
            response.value.banks.size shouldBeLessThanOrEqual 30 // limit = 30
        }

        @Test
        fun `should fetch primary validator details from config`() = runBlockingTest {
            val response = getDataSource.validatorDetails()

            check(response is Outcome.Success)
            response.value.nodeType should contain("VALIDATOR")
            response.value.rootAccountFile shouldBe "http://20.188.33.93/media/root_account_file.json"
            response.value.ipAddress should contain("172.19.0.13")
        }

        @Test
        fun `should fetch list of 20 available accounts from primary validator`() = runBlockingTest {
            val response = getDataSource.accountsFromValidator(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.results.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 20
            response.value.results.size shouldBeLessThanOrEqual 20
        }

        @Test
        fun `should fetch list of 20 validators successfully`() = runBlockingTest {
            val response = getDataSource.validators(paginationTwenty)

            check(response is Outcome.Success)
            response.value.results.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 20
            response.value.results.size shouldBeLessThanOrEqual 20
        }

        @Test
        fun `should fetch list of 30 available accounts from primary validator`() = runBlockingTest {
            val response = getDataSource.accountsFromValidator(PaginationOptions(20, 20))

            check(response is Outcome.Success)
            response.value.results.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 0
            response.value.results.size shouldBeLessThanOrEqual 30
        }

        @Test
        fun `should fetch list of 30 validators successfully`() = runBlockingTest {
            val response = getDataSource.validators(paginationThirty)

            check(response is Outcome.Success)
            response.value.results.shouldNotBeEmpty()
            response.value.count shouldBeGreaterThan 0
            response.value.results.size shouldBeLessThanOrEqual 30
        }

        @Test
        fun `should fetch account balance successfully from primary validator`() = runBlockingTest {
            val accountNumber = Some.accountNumber
            val response = getDataSource.accountBalance(accountNumber)

            check(response is Outcome.Success)
            response.value.balance shouldBe Some.balance
        }

        @Test
        fun `should fetch account balance lock successfully from primary validator`() = runBlockingTest {
            val accountNumber = Some.accountNumber
            val response = getDataSource.accountBalanceLock(accountNumber)

            check(response is Outcome.Success)
            response.value.balanceLock shouldBe Some.balanceLock
        }

        @Test
        fun `should fetch single validator successfully`() = runBlockingTest {
            val nodeIdentifier =
                "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"
            val response = getDataSource.validator(nodeIdentifier)

            check(response is Outcome.Success)
            response.value.nodeIdentifier should contain(nodeIdentifier)
            response.value.ipAddress should contain("127.0.0.1")
        }

        @Test
        fun `should fetch confirmation blocks successfully`() = runBlockingTest {
            val response = getDataSource.confirmationBlocks(Some.blockIdentifier)

            check(response is Outcome.Success)
            response.value.message.blockIdentifier shouldBe Some.blockIdentifier
        }
    }

    @Nested
    @DisplayName("Primary Validator: Given empty or invalid response body")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PrimaryGivenInvalidResponseBody {

        private val pagination = Mocks.paginationOptionsDefault()

        @BeforeEach
        fun given() {
            every { networkClient.defaultClient } returns primaryMockEngine.getEmptySuccess()
        }

        @Test
        fun `should return error outcome for banks IOException`() = runBlockingTest {
            val response = getDataSource.banksFromValidator(pagination)

            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
        }

        @Test
        fun `should return error outcome for empty accounts from primary validator`() = runBlockingTest {
            val response = getDataSource.accountsFromValidator(Mocks.paginationOptionsDefault())

            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
        }

        @Test
        fun `should return error outcome for empty validators`() = runBlockingTest {
            val response = getDataSource.validators(pagination)

            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            response.message shouldBe ErrorMessages.EMPTY_LIST_MESSAGE
        }
    }
}
