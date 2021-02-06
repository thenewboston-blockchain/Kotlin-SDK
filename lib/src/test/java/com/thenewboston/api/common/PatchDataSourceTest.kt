package com.thenewboston.api.common

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.utils.mockEngine.bank.BankApiMockEngine
import com.thenewboston.utils.Mocks
import com.thenewboston.utils.Some
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.string.beEmpty
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
class PatchDataSourceTest {

    @MockK
    lateinit var networkClient: NetworkClient

    private val mockEngine = BankApiMockEngine()

    private lateinit var patchDataSource: PatchDataSource

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)

        patchDataSource = PatchDataSource(networkClient)
    }

    @Nested
    @DisplayName("Given successful request")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenSucceedingRequest {

        @BeforeEach
        fun setup() {
            every { networkClient.defaultClient } returns mockEngine.patchSuccess()
        }

        @Test
        fun `should send bank trust successfully`() = runBlockingTest {
            // given
            val request = Mocks.trustRequest(3.14)

            // when
            val response = patchDataSource.doUpdateBankTrust(request)

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
            val response = patchDataSource.doUpdateAccount(accountNumber, trustRequest)

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
            val response = patchDataSource.doSendInvalidBlock(request)

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
            val response = patchDataSource.doSendBlock(request)

            // then
            check(response is Outcome.Success)
            response.value.id shouldNot beEmpty()
            response.value.balanceKey shouldBe request.message.balanceKey
        }
    }

    @Nested
    @DisplayName("Given empty or invalid response body")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
                val response = patchDataSource.doUpdateBankTrust(request)

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
                val response = patchDataSource.doUpdateAccount(accountNumber, request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                val message = "Received unexpected response when updating trust level of account $accountNumber"
                response.message shouldBe message
            }

        @Test
        fun `should return error outcome when receiving invalid response`() = runBlockingTest {
            // given
            val request = Mocks.postInvalidBlockRequest()

            // when
            val response = patchDataSource.doSendInvalidBlock(request)

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            val message =
                "Received invalid response when sending invalid block with identifier ${request.message.blockIdentifier}"
            response.message shouldBe message
        }

        @Test
        fun `should return error outcome when receiving invalid response for sending block`() =
            runBlockingTest {
                // given
                val request = Mocks.postBlockRequest()

                // when
                val response = patchDataSource.doSendBlock(request)

                // then
                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                val message = "Received invalid response when sending block with balance key: ${request.message.balanceKey}"
                response.message shouldBe message
            }
    }
}
