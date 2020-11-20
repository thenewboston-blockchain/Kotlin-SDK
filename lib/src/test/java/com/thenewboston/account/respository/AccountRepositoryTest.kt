package com.thenewboston.account.respository

import com.thenewboston.account.datasource.AccountDataSource
import com.thenewboston.account.repository.AccountRepository
import com.thenewboston.common.http.Outcome
import com.thenewboston.utils.Mocks
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
class AccountRepositoryTest {

    private val accountDataSource: AccountDataSource = mockk()

    private val repository: AccountRepository = AccountRepository(accountDataSource)

    @Nested
    @DisplayName("Given the data source returns an error...")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GivenDataSourceError {

        @BeforeAll
        fun givenDataSourceError() {
            val errorOutcome = Outcome.Error("", IOException())
            coEvery { accountDataSource.fetchAccounts() } returns errorOutcome
        }

        @Nested
        @DisplayName("When fetching all accounts...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenFetchingAllAccounts {

            @Test
            fun `should call data source and propagate the error outcome`() = runBlockingTest {
                // when
                val result = repository.accounts()

                // then
                coVerify { accountDataSource.fetchAccounts() }
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
            val successOutcome = Outcome.Success(Mocks.accounts())
            coEvery { accountDataSource.fetchAccounts() } returns successOutcome

        }

        @Nested
        @DisplayName("When fetching all accounts...")
        @TestInstance(Lifecycle.PER_CLASS)
        inner class WhenFetchingAllValidators {

            @Test
            fun `should call data source and return the success outcome`() = runBlockingTest {
                // when
                val result = repository.accounts()

                // then
                coVerify { accountDataSource.fetchAccounts() }
                assertTrue(result is Outcome.Success)
            }
        }

    }
}
