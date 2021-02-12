package com.thenewboston.api.primaryvalidatorapi.datasource

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
import io.kotest.matchers.string.contain
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
class PrimaryDataSourceTest {

    @MockK
    lateinit var getDataSource: GetDataSource

    @MockK
    lateinit var postDataSource: PostDataSource

    private lateinit var primaryDataSource: PrimaryDataSource

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)

        primaryDataSource = PrimaryDataSource(getDataSource, postDataSource)
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
            fun `should fetch single bank successfully`() = runBlockingTest {
                val nodeIdentifier = Some.nodeIdentifier

                coEvery { getDataSource.bankFromValidator(nodeIdentifier) } returns Outcome.Success(Mocks.bankFromValidator())

                val response = getDataSource.bankFromValidator(nodeIdentifier)

                check(response is Outcome.Success)
                response.value.nodeIdentifier should contain(nodeIdentifier)
                response.value.ipAddress should contain("127.0.0.1")
            }

            @Test
            fun `should fetch list of 20 available banks sent from validator`() = runBlockingTest {
                val value = Mocks.banksFromValidator(paginationTwenty)
                coEvery { getDataSource.banksFromValidator(paginationTwenty) } returns Outcome.Success(value)

                val response = primaryDataSource.fetchBanksFromValidator(paginationTwenty)

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20 // offset = 20
                response.value.banks.size shouldBeLessThanOrEqual 20 // limit = 30
            }

            @Test
            fun `should fetch list of 30 available banks sent from validator`() = runBlockingTest {
                val value = Mocks.banksFromValidator(paginationThirty)
                coEvery { getDataSource.banksFromValidator(paginationThirty) } returns Outcome.Success(value)

                val response = primaryDataSource.fetchBanksFromValidator(paginationThirty)

                check(response is Outcome.Success)
                response.value.banks.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0 // offset = 0
                response.value.banks.size shouldBeLessThanOrEqual 30 // limit = 30
            }

            @Test
            fun `should fetch primary validator details from config`() = runBlockingTest {
                coEvery {
                    getDataSource.primaryValidatorDetails()
                } returns Outcome.Success(Mocks.primaryValidatorDetails())

                val response = primaryDataSource.fetchPrimaryValidatorDetails()

                check(response is Outcome.Success)
                response.value.nodeType shouldBe "PRIMARY_VALIDATOR"
                response.value.rootAccountFile shouldBe "http://20.188.33.93/media/root_account_file.json"
                response.value.ipAddress should contain("172.19.0.13")
            }

            @Test
            fun `should fetch list of 20 accounts successfully from primary validator`() = runBlockingTest {
                val value = Mocks.accountsFromValidator(paginationTwenty)
                coEvery { getDataSource.accountsFromValidator(paginationTwenty) } returns Outcome.Success(value)

                val response = primaryDataSource.fetchAccountsFromValidator(paginationTwenty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.results.size shouldBeLessThanOrEqual 20
            }

            @Test
            fun `should fetch list of 20 validators successfully`() = runBlockingTest {
                val value = Mocks.validators(paginationTwenty)

                coEvery { getDataSource.validators(paginationTwenty) } returns Outcome.Success(value)
                val response = primaryDataSource.fetchValidators(paginationTwenty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 20
                response.value.results.size shouldBeLessThanOrEqual 20
            }

            @Test
            fun `should fetch list of 30 accounts successfully from primary validator`() = runBlockingTest {
                val value = Mocks.accountsFromValidator(paginationThirty)
                coEvery { getDataSource.accountsFromValidator(paginationThirty) } returns Outcome.Success(value)

                val response = primaryDataSource.fetchAccountsFromValidator(paginationThirty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.results.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch list of 30 validators successfully`() = runBlockingTest {
                val value = Mocks.validators(paginationThirty)
                coEvery { getDataSource.validators(paginationThirty) } returns Outcome.Success(value)

                val response = primaryDataSource.fetchValidators(paginationThirty)

                check(response is Outcome.Success)
                response.value.results.shouldNotBeEmpty()
                response.value.count shouldBeGreaterThan 0
                response.value.results.size shouldBeLessThanOrEqual 30
            }

            @Test
            fun `should fetch account balance successfully from primary validator`() = runBlockingTest {
                val accountNumber = Some.accountNumber
                val value = Mocks.accountBalance()
                coEvery { getDataSource.accountBalance(accountNumber) } returns Outcome.Success(value)

                val response = primaryDataSource.fetchAccountBalance(accountNumber)

                check(response is Outcome.Success)
                response.value.balance shouldBe Some.balance
            }

            @Test
            fun `should fetch account balance lock successfully from primary validator`() = runBlockingTest {
                val accountNumber = Some.accountNumber
                val value = Mocks.accountBalanceLock()
                coEvery { getDataSource.accountBalanceLock(accountNumber) } returns Outcome.Success(value)

                val response = primaryDataSource.fetchAccountBalanceLock(accountNumber)

                check(response is Outcome.Success)
                response.value.balanceLock shouldBe Some.balanceLock
            }

            @Test
            fun `should fetch single validator successfully`() = runBlockingTest {
                val nodeIdentifier =
                    "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"

                coEvery { getDataSource.validator(nodeIdentifier) } returns Outcome.Success(Mocks.validator())

                val response = primaryDataSource.fetchValidator(nodeIdentifier)

                check(response is Outcome.Success)
                response.value.nodeIdentifier should contain(nodeIdentifier)
                response.value.ipAddress should contain("127.0.0.1")
            }

            @Test
            fun `should fetch confirmations blocks successfully`() = runBlockingTest {
                val blockIdentifier = Some.blockIdentifier

                coEvery { getDataSource.confirmationBlocks(blockIdentifier) } returns Outcome.Success(Mocks.confirmationBlocks())

                val response = primaryDataSource.fetchConfirmationBlocks(Some.blockIdentifier)

                check(response is Outcome.Success)
                response.value.message.blockIdentifier shouldBe Some.blockIdentifier
            }

            @Test
            fun `should send bank block request successfully`() = runBlockingTest {
                val request = Mocks.bankBlockRequest()
                val value = Mocks.bankBlock()

                coEvery { postDataSource.doSendBankBlock(request) } returns Outcome.Success(value)

                val response = primaryDataSource.sendBankBlock(request)

                check(response is Outcome.Success)
                response.value.accountNumber shouldBe Some.accountNumber
                response.value.message.balanceKey shouldBe Some.balanceKey
            }

            @Test
            fun `should send connection request successfully`() = runBlockingTest {
                val request = Mocks.connectionRequest()
                val message = "Successfully sent connection requests"

                coEvery { postDataSource.doSendConnectionRequests(request) } returns Outcome.Success(message)

                val response = primaryDataSource.sendConnectionRequest(request)

                check(response is Outcome.Success)
                response.value shouldBe message
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
            fun `should return error outcome for single bank`() = runBlockingTest {
                val nodeIdentifier = Some.nodeIdentifier
                val message = "Failed to retrieve bank from validator"

                coEvery { getDataSource.bankFromValidator(nodeIdentifier) } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchBankFromValidator(nodeIdentifier)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for banks IOException`() = runBlockingTest {
                val message = "Failed to retrieve banks from validator"
                coEvery { getDataSource.banksFromValidator(pagination) } returns Outcome.Error(message, IOException())
                val response = primaryDataSource.fetchBanksFromValidator(pagination)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for primary validator details IOException`() = runBlockingTest {

                val message = "Failed to retrieve primary validator details"
                coEvery { getDataSource.primaryValidatorDetails() } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchPrimaryValidatorDetails()

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for list of accounts from primary validator IOException`() = runBlockingTest {
                val message = "Failed to retrieve accounts from primary validator"
                coEvery {
                    getDataSource.accountsFromValidator(pagination)
                } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchAccountsFromValidator(pagination)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for list of validators IOException`() = runBlockingTest {
                val message = "Could not fetch list of accounts"
                coEvery { getDataSource.validators(pagination) } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchValidators(pagination)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for account balance IOException`() = runBlockingTest {
                val accountNumber = Some.accountNumber
                val message = "Failed to retrieve account balance from primary validator"
                coEvery { getDataSource.accountBalance(accountNumber) } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchAccountBalance(accountNumber)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for account balance lock IOException`() = runBlockingTest {
                val accountNumber = Some.accountNumber
                val message = "Failed to retrieve account balance lock from primary validator"
                coEvery {
                    getDataSource.accountBalanceLock(accountNumber)
                } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchAccountBalanceLock(accountNumber)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for single validator`() = runBlockingTest {
                val nodeIdentifier = "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53"
                val message = "Could not fetch validator with NID $nodeIdentifier"

                coEvery { getDataSource.validator(nodeIdentifier) } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchValidator(nodeIdentifier)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for confirmation blocks`() = runBlockingTest {
                val message = "Could not fetch validator with block identifier $Some.blockIdentifier"

                coEvery { getDataSource.validator(Some.blockIdentifier) } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchValidator(Some.blockIdentifier)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for bank block requests`() = runBlockingTest {
                val message = "Could not send bank block request"
                val request = Mocks.bankBlockRequest()

                coEvery {
                    postDataSource.doSendBankBlock(request)
                } returns Outcome.Error(message, IOException())

                val response = postDataSource.doSendBankBlock(request)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }

            @Test
            fun `should return error outcome for connection requests`() = runBlockingTest {
                val message = "Could not send connection request"
                val request = Mocks.connectionRequest()

                coEvery {
                    postDataSource.doSendConnectionRequests(request)
                } returns Outcome.Error(message, IOException())

                val response = postDataSource.doSendConnectionRequests(request)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }
        }
    }
}
