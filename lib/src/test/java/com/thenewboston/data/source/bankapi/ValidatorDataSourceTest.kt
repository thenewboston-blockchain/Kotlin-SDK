package com.thenewboston.data.source.bankapi

import com.thenewboston.Config
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import io.ktor.client.features.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle


/**
 * Checks that requests to a bank's validator endpoints using the [ValidatorDataSource] work as expected.
 */
@KtorExperimentalAPI
class ValidatorDataSourceTest {

    private val networkClient = NetworkClient(BankConfig(
            ipAddress = Config.IP_ADDRESS,
            port = Config.PORT,
            protocol = Config.PROTOCOL
    ))

    private val validatorDataSource = ValidatorDataSource(networkClient)

    @Nested
    @DisplayName("Given a valid request that should succeed...")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GivenValidRequests {

        @Test
        fun `should fetch list of validators successfully`() = runBlocking {
            // when
            val body = validatorDataSource.fetchValidators()

            // then
            check(body is Outcome.Success)
            assertTrue(body.value.count > 0)
            assertTrue(body.value.results.isNotEmpty())
        }

        @Test
        fun `should fetch single validator successfully`() = runBlocking {
            // given
            val nodeIdentifier = "2262026a562b0274163158e92e8fbc4d28e519bc5ba8c1cf403703292be84a51"

            // when
            val body = validatorDataSource.fetchValidator(nodeIdentifier)

            // then
            check(body is Outcome.Success)
            assertEquals(nodeIdentifier, body.value.nodeIdentifier)
            assertEquals("54.183.17.224", body.value.ipAddress)
        }
    }

    @Nested
    @DisplayName("Given invalid request that should fail...")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GivenInvalidRequest {

        @Test
        fun `should return error outcome for nonexistent node identifier`() = runBlocking {
            // given
            val nodeIdentifier = "foo"

            // when
            val body = validatorDataSource.fetchValidator(nodeIdentifier)

            // then
            check(body is Outcome.Error)
            assertTrue(body.cause is ClientRequestException)
        }
    }
}
