package com.thenewboston.api.bankapi.repository

import com.thenewboston.api.bankapi.datasource.BankDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import com.thenewboston.utils.Mocks
import io.kotest.matchers.should
import io.kotest.matchers.types.beInstanceOf
import io.ktor.util.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

@ExperimentalCoroutinesApi
@KtorExperimentalAPI
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

        repository.banks() should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify banks result is success`() = runBlockingTest {
        coEvery { bankDataSource.fetchBanks() } returns Outcome.Success(Mocks.banks())

        repository.banks() should beInstanceOf<Outcome.Success<*>>()
    }

    @Test
    fun `verify detail result is error`() = runBlockingTest {
        coEvery {
            bankDataSource.fetchBankDetails(any())
        } returns Outcome.Error("", IOException())

        repository.bankDetail(BankConfig()) should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify detail result is success`() = runBlockingTest {
        coEvery {
            bankDataSource.fetchBankDetails(any())
        } returns Outcome.Success(Mocks.bankDetails())

        repository.bankDetail(BankConfig()) should beInstanceOf<Outcome.Success<*>>()
    }

    @Test
    fun `verify transactions result is error`() = runBlockingTest {
        coEvery { bankDataSource.fetchBankTransactions() } returns
            Outcome.Error("", IOException())

        repository.bankTransactions() should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify transactions result is success`() = runBlockingTest {
        coEvery { bankDataSource.fetchBankTransactions() } returns
            Outcome.Success(Mocks.bankTransactions())

        repository.bankTransactions() should beInstanceOf<Outcome.Success<*>>()
    }

    @Test
    fun `verify validators result is error`() = runBlockingTest {
        coEvery { bankDataSource.fetchValidators() } returns Outcome.Error("", IOException())

        // when
        val result = repository.validators()

        // then
        coVerify { bankDataSource.fetchValidators() }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify single validator is error outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchValidator(any()) } returns Outcome.Error("", IOException())

        val nodeIdentifier = "someNodeIdentifier"

        // when
        val result = repository.validator(nodeIdentifier)

        // then
        coVerify { bankDataSource.fetchValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify validators result is success outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchValidators() } returns Outcome.Success(Mocks.validators())

        // when
        val result = repository.validators()

        // then
        coVerify { bankDataSource.fetchValidators() }
        result should beInstanceOf<Outcome.Success<*>>()
    }

    @Test
    fun `verify single validator result is success outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchValidator(any()) } returns Outcome.Success(Mocks.validator())

        val nodeIdentifier = "someNodeIdentifier"

        // when
        val result = repository.validator(nodeIdentifier)

        // then
        coVerify { bankDataSource.fetchValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Success<*>>()
    }

    @Test
    fun `verify list of accounts is error outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchAccounts() } returns Outcome.Error("", IOException())

        // when
        val result = repository.accounts()

        // then
        coVerify { bankDataSource.fetchAccounts() }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify list of accounts is success outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchAccounts() } returns Outcome.Success(Mocks.accounts())

        // when
        val result = repository.accounts()

        // then
        coVerify { bankDataSource.fetchAccounts() }
        result should beInstanceOf<Outcome.Success<*>>()
    }

    @Test
    fun `verify list of blocks is error outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchBlocks() } returns Outcome.Error("", IOException())

        // when
        val result = repository.blocks()

        // then
        coVerify { bankDataSource.fetchBlocks() }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify list of blocks is success outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchBlocks() } returns Outcome.Success(Mocks.blocks())

        // when
        val result = repository.blocks()

        // then
        coVerify { bankDataSource.fetchBlocks() }
        result should beInstanceOf<Outcome.Success<*>>()
    }
}
