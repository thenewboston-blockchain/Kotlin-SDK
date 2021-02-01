package com.thenewboston.api.primaryvalidatorapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.utils.Mocks
import com.thenewboston.utils.Some
import io.kotest.matchers.collections.shouldNotBeEmpty
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
            fun `should fetch primary validator details from config`() = runBlockingTest {
                coEvery { getDataSource.primaryValidatorDetails() } returns Outcome.Success(Mocks.primaryValidatorDetails())

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
        }
    }

    @Nested
    @DisplayName("Given request that should fail")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GivenFailingRequest {

        private val pagination = Mocks.paginationOptionsDefault()

        @Nested
        @DisplayName("When performing GET request...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenGetRequest {
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
                coEvery { getDataSource.accountsFromValidator(pagination) } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchAccountsFromValidator(pagination)

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
                coEvery { getDataSource.accountBalanceLock(accountNumber) } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchAccountBalanceLock(accountNumber)

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }
        }
    }
}
