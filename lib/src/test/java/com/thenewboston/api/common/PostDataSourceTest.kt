package com.thenewboston.api.common

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.utils.mockEngine.bank.BankApiMockEngine
import com.thenewboston.utils.Mocks
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
class PostDataSourceTest {

    @MockK
    lateinit var networkClient: NetworkClient

    private val mockEngine = BankApiMockEngine()

    private lateinit var postDataSource: PostDataSource

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)

        postDataSource = PostDataSource(networkClient)
    }

    @Nested
    @DisplayName("Given successful request")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenSucceedingRequest {

        @BeforeEach
        fun setup() {
            every { networkClient.defaultClient } returns mockEngine.postSuccess()
        }

        @Test
        fun `should send validator confirmation successfully`() = runBlockingTest {
            val request = Mocks.confirmationServiceRequest()

            // when
            val response = postDataSource.doSendConfirmationServices(request)

            // then
            check(response is Outcome.Success)
            response.value.start shouldBe request.message.start.toString()
            response.value.end shouldBe request.message.end.toString()
        }

        @Test
        fun `should send upgrade notice successfully`() = runBlockingTest {
            val request = Mocks.upgradeNoticeRequest()

            val response = postDataSource.doSendUpgradeNotice(request)

            check(response is Outcome.Success)
            response.value shouldNot beEmpty()
            response.value shouldBe "Successfully sent upgrade notice"
        }

        @Test
        fun `should return success with clean status `() = runBlockingTest {
            // given
            val request = Mocks.postCleanRequest()

            // when
            val response = postDataSource.doSendClean(request)

            // then
            check(response is Outcome.Success)
            response.value.cleanStatus shouldNot beEmpty()
            response.value.cleanStatus shouldBe request.data.clean
        }

        @Test
        fun `should return success with crawl status `() = runBlockingTest {
            // given
            val request = Mocks.postCrawlRequest()

            // when
            val response = postDataSource.doSendCrawl(request)

            // then
            check(response is Outcome.Success)
            response.value.crawlStatus shouldNot beEmpty()
            response.value.crawlStatus shouldBe request.data.crawl
        }

        @Test
        fun `should send connection requests successfully`() = runBlockingTest {
            val request = Mocks.connectionRequest()

            val response = postDataSource.doSendConnectionRequests(request)

            check(response is Outcome.Success)
            response.value shouldNot beEmpty()
            response.value shouldBe "Successfully sent connection requests"
        }
    }

    @Nested
    @DisplayName("Given empty or invalid response body")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GivenInvalidResponseBody {

        @BeforeEach
        fun given() {
            every { networkClient.defaultClient } returns mockEngine.postInvalidSuccess()
        }

        @Test
        fun `should return error outcome for sending invalid request for confirmation services`() = runBlockingTest {
            // given
            val request = Mocks.confirmationServiceRequest()

            // when
            val response = postDataSource.doSendConfirmationServices(request)

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            val nodeIdentifier = request.nodeIdentifier
            val message =
                "Received invalid response sending confirmation services with node identifier: $nodeIdentifier"
            response.message shouldBe message
        }

        @Test
        fun `should return error outcome when receiving invalid response for sending clean`() = runBlockingTest {
            // given
            val request = Mocks.postCleanRequest()

            // when
            val response = postDataSource.doSendClean(request)

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            val message = "Received invalid response when sending block with clean: ${request.data.clean}"
            response.message shouldBe message
        }

        @Test
        fun `should return error outcome when receiving invalid response for sending crawl`() = runBlockingTest {
            // given
            val request = Mocks.postCrawlRequest()

            // when
            val response = postDataSource.doSendCrawl(request)

            // then
            check(response is Outcome.Error)
            response.cause should beInstanceOf<IOException>()
            val message = "Received invalid response when sending block with crawl: ${request.data.crawl}"
            response.message shouldBe message
        }
    }
}
