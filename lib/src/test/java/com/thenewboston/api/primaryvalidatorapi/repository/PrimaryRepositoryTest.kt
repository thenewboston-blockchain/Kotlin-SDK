package com.thenewboston.api.primaryvalidatorapi.repository

import com.thenewboston.api.primaryvalidatorapi.datasource.PrimaryDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.primaryvalidatorapi.accountdto.AccountBalance
import com.thenewboston.data.dto.primaryvalidatorapi.accountdto.AccountBalanceLock
import com.thenewboston.data.dto.primaryvalidatorapi.accountdto.AccountFromValidatorList
import com.thenewboston.data.dto.primaryvalidatorapi.configdto.PrimaryValidatorDetails
import com.thenewboston.utils.Mocks
import com.thenewboston.utils.Some
import io.kotest.matchers.should
import io.kotest.matchers.types.beInstanceOf
import io.ktor.util.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.IOException

@ExperimentalCoroutinesApi
@KtorExperimentalAPI
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PrimaryRepositoryTest {

    @MockK
    lateinit var primaryDataSource: PrimaryDataSource

    private lateinit var repository: PrimaryRepository

    private val pagination = Mocks.paginationOptionsDefault()

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)

        repository = PrimaryRepository(primaryDataSource)
    }

    @Test
    fun `verify primary validator details is error`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchPrimaryValidatorDetails()
        } returns Outcome.Error("", IOException())

        repository.primaryValidatorDetails() should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify primary validator detail is success`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchPrimaryValidatorDetails()
        } returns Outcome.Success(Mocks.primaryValidatorDetails())

        repository.primaryValidatorDetails() should beInstanceOf<Outcome.Success<PrimaryValidatorDetails>>()
    }

    @Test
    fun `verify list of accounts from primary validator is error outcome`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchAccountsFromValidator(pagination)
        } returns Outcome.Error("", IOException())

        val result = repository.accountsFromValidator(0, 20)

        coVerify { primaryDataSource.fetchAccountsFromValidator(pagination) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify list of accounts from primary validator is success outcome`() = runBlockingTest {
        coEvery { primaryDataSource.fetchAccountsFromValidator(pagination) } returns Outcome.Success(Mocks.accountsFromValidator())

        val result = repository.accountsFromValidator(0, 20)

        coVerify { primaryDataSource.fetchAccountsFromValidator(pagination) }
        result should beInstanceOf<Outcome.Success<AccountFromValidatorList>>()
    }

    @Test
    fun `verify list of account balance from primary validator is error outcome`() = runBlockingTest {
        val accountNumber = Some.accountNumber
        coEvery {
            primaryDataSource.fetchAccountBalance(accountNumber)
        } returns Outcome.Error("", IOException())

        val result = repository.accountBalance(accountNumber)

        coVerify { primaryDataSource.fetchAccountBalance(accountNumber) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify list of account balance from primary validator is success outcome`() = runBlockingTest {
        val accountNumber = Some.accountNumber

        coEvery {
            primaryDataSource.fetchAccountBalance(accountNumber)
        } returns Outcome.Success(Mocks.accountBalance())

        val result = repository.accountBalance(accountNumber)

        coVerify { primaryDataSource.fetchAccountBalance(accountNumber) }
        result should beInstanceOf<Outcome.Success<AccountBalance>>()
    }

    @Test
    fun `verify list of account balance lock from primary validator is error outcome`() = runBlockingTest {
        val accountNumber = Some.accountNumber
        coEvery {
            primaryDataSource.fetchAccountBalanceLock(accountNumber)
        } returns Outcome.Error("", IOException())

        val result = repository.accountBalanceLock(accountNumber)

        coVerify { primaryDataSource.fetchAccountBalanceLock(accountNumber) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify list of account balance lock from primary validator is success outcome`() = runBlockingTest {
        val accountNumber = Some.accountNumber

        coEvery {
            primaryDataSource.fetchAccountBalanceLock(accountNumber)
        } returns Outcome.Success(Mocks.accountBalanceLock())

        val result = repository.accountBalanceLock(accountNumber)

        coVerify { primaryDataSource.fetchAccountBalanceLock(accountNumber) }
        result should beInstanceOf<Outcome.Success<AccountBalanceLock>>()
    }
}
