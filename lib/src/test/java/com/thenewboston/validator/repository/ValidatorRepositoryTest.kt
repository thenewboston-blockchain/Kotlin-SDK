package com.thenewboston.validator.repository

import com.thenewboston.common.http.Outcome
import com.thenewboston.utils.Mocks
import com.thenewboston.validator.datasource.ValidatorDataSource
import io.ktor.util.KtorExperimentalAPI
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

/**
 * Mocks the data source layer and asserts that the repository calls its data source as expected.
 */
@ExperimentalCoroutinesApi
@KtorExperimentalAPI
@TestInstance(Lifecycle.PER_CLASS)
class ValidatorRepositoryTest {

    private val validatorDataSource: ValidatorDataSource = mockk()

    private val repository: ValidatorRepository = ValidatorRepository(validatorDataSource)

    @Nested
    @DisplayName("Given the data source returns an error...")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GivenDataSourceError {

        @BeforeAll
        fun givenDataSourceError() {
            val errorOutcome = Outcome.Error("", IOException())
            coEvery { validatorDataSource.fetchValidators() } returns errorOutcome
            coEvery { validatorDataSource.fetchValidator(any()) } returns errorOutcome
        }

        @Nested
        @DisplayName("When fetching all validators...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenFetchingAllValidators {

            @Test
            fun `should call data source and propagate the error outcome`() = runBlockingTest {
                // when
                val result = repository.validators()

                // then
                coVerify { validatorDataSource.fetchValidators() }
                assertTrue(result is Outcome.Error)
            }
        }

        @Nested
        @DisplayName("When fetching a single validator...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenFetchingSingleValidator {

            private val nodeIdentifier = "someNodeIdentifier"

            @Test
            fun `should call data source and propagate the error outcome`() = runBlockingTest {
                // when
                val result = repository.validator(nodeIdentifier)

                // then
                coVerify { validatorDataSource.fetchValidator(nodeIdentifier) }
                assertTrue(result is Outcome.Error)
            }
        }
    }

    @Nested
    @DisplayName("Given the data source responds successfully...")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GivenDataSourceSuccess {

        @BeforeAll
        fun givenDataSourceSuccess() {
            val successOutcome = Outcome.Success(Mocks.validators())
            coEvery { validatorDataSource.fetchValidators() } returns successOutcome

            val successOutcomeSingle = Outcome.Success(Mocks.validator())
            coEvery { validatorDataSource.fetchValidator(any()) } returns successOutcomeSingle
        }

        @Nested
        @DisplayName("When fetching all validators...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenFetchingAllValidators {

            @Test
            fun `should call data source and return the success outcome`() = runBlockingTest {
                // when
                val result = repository.validators()

                // then
                coVerify { validatorDataSource.fetchValidators() }
                assertTrue(result is Outcome.Success)
            }
        }

        @Nested
        @DisplayName("When fetching a single validator...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenFetchingSingleValidator {

            private val nodeIdentifier = "someNodeIdentifier"

            @Test
            fun `should call data source and return the success outcome`() = runBlockingTest {
                // when
                val result = repository.validator(nodeIdentifier)

                // then
                coVerify { validatorDataSource.fetchValidator(nodeIdentifier) }
                assertTrue(result is Outcome.Success)
            }
        }
    }
}
