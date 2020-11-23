package com.thenewboston.api.bankapi.repository

import com.thenewboston.api.bankapi.datasource.BankDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import com.thenewboston.utils.Mocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class BankRepositoryTest {

    @MockK
    lateinit var bankDataSource: BankDataSource

    private lateinit var repository: BankRepository

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)

        repository = BankRepository(bankDataSource)
    }

    @Test
    fun `verify banks result is error`() = runBlockingTest {
        coEvery { bankDataSource.fetchBanks() } returns Outcome.Error("", IOException())

        assertTrue(repository.banks() is Outcome.Error)
    }

    @Test
    fun `verify banks result is success`() = runBlockingTest {
        coEvery { bankDataSource.fetchBanks() } returns Outcome.Success(Mocks.banks())

        assertTrue(repository.banks() is Outcome.Success)
    }

    @Test
    fun `verify detail result is error`() = runBlockingTest {
        coEvery {
            bankDataSource.fetchBankDetails(any())
        } returns Outcome.Error("", IOException())

        assertTrue(repository.bankDetail(BankConfig()) is Outcome.Error)
    }

    @Test
    fun `verify detail result is success`() = runBlockingTest {
        coEvery {
            bankDataSource.fetchBankDetails(any())
        } returns Outcome.Success(Mocks.bankDetails())

        assertTrue(repository.bankDetail(BankConfig()) is Outcome.Success)
    }

    @Test
    fun `verify transactions result is error`() = runBlockingTest {
        coEvery { bankDataSource.fetchBankTransactions() } returns
            Outcome.Error("", IOException())

        assertTrue(repository.bankTransactions() is Outcome.Error)
    }

    @Test
    fun `verify transactions result is success`() = runBlockingTest {
        coEvery { bankDataSource.fetchBankTransactions() } returns
            Outcome.Success(Mocks.bankTransactions())

        assertTrue(repository.bankTransactions() is Outcome.Success)
    }

    @Test
    fun `verify validators result is error`() = runBlockingTest {
        coEvery { bankDataSource.fetchValidators() } returns Outcome.Error("", IOException())

        // when
        val result = repository.validators()

        // then
        coVerify { bankDataSource.fetchValidators() }
        assertTrue(result is Outcome.Error)
    }

    @Test
    fun `verify single validator is error outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchValidator(any()) } returns Outcome.Error("", IOException())

        val nodeIdentifier = "someNodeIdentifier"

        // when
        val result = repository.validator(nodeIdentifier)

        // then
        coVerify { bankDataSource.fetchValidator(nodeIdentifier) }
        assertTrue(result is Outcome.Error)
    }

    @Test
    fun `verify validators result is success outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchValidators() } returns Outcome.Success(Mocks.validators())

        // when
        val result = repository.validators()

        // then
        coVerify { bankDataSource.fetchValidators() }
        assertTrue(result is Outcome.Success)
    }

    @Test
    fun `verify single validator result is success outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchValidator(any()) } returns Outcome.Success(Mocks.validator())

        val nodeIdentifier = "someNodeIdentifier"

        // when
        val result = repository.validator(nodeIdentifier)

        // then
        coVerify { bankDataSource.fetchValidator(nodeIdentifier) }
        assertTrue(result is Outcome.Success)
    }

    @Test
    fun `verify list of accounts is error outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchAccounts() } returns Outcome.Error("", IOException())

        // when
        val result = repository.accounts()

        // then
        coVerify { bankDataSource.fetchAccounts() }
        assertTrue(result is Outcome.Error)
    }

    @Test
    fun `verify list of accounts is success outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchAccounts() } returns Outcome.Success(Mocks.accounts())

        // when
        val result = repository.accounts()

        // then
        coVerify { bankDataSource.fetchAccounts() }
        assertTrue(result is Outcome.Success)
    }
}
