package com.thenewboston.api.primaryvalidatorapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.utils.Mocks
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
            fun `should fetch primary validator details from config`() = runBlockingTest {
                coEvery { getDataSource.primaryValidatorDetails() } returns Outcome.Success(Mocks.primaryValidatorDetails())

                val response = primaryDataSource.fetchPrimaryValidatorDetails()

                check(response is Outcome.Success)
                response.value.nodeType shouldBe "PRIMARY_VALIDATOR"
                response.value.rootAccountFile shouldBe "http://20.188.33.93/media/root_account_file.json"
                response.value.ipAddress should contain("172.19.0.13")
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
            @Test
            fun `should return error outcome for primary validator details IOException`() = runBlockingTest {

                val message = "Failed to retrieve primary validator details"
                coEvery { getDataSource.primaryValidatorDetails() } returns Outcome.Error(message, IOException())

                val response = primaryDataSource.fetchPrimaryValidatorDetails()

                check(response is Outcome.Error)
                response.cause should beInstanceOf<IOException>()
                response.message shouldBe message
            }
        }
    }
}
