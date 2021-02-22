package com.thenewboston.api.primaryvalidatorapi.repository

import com.thenewboston.api.primaryvalidatorapi.datasource.PrimaryDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.common.response.*
import com.thenewboston.data.dto.primaryvalidatorapi.bankblockdto.BankBlock
import com.thenewboston.data.dto.primaryvalidatorapi.bankdto.BankFromValidator
import com.thenewboston.data.dto.primaryvalidatorapi.bankdto.BankFromValidatorList
import com.thenewboston.utils.Mocks
import com.thenewboston.utils.PaginationOptions
import com.thenewboston.utils.Some
import io.kotest.matchers.should
import io.kotest.matchers.types.beInstanceOf
import io.ktor.util.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

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
    fun `verify single bank from validator is error outcome`() = runBlockingTest {
        val nodeIdentifier = Some.nodeIdentifier
        coEvery { primaryDataSource.fetchBankFromValidator(nodeIdentifier) } returns Outcome.Error("", IOException())

        val result = repository.bankFromValidator(nodeIdentifier)

        coVerify { primaryDataSource.fetchBankFromValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify single bank from validator is success outcome`() = runBlockingTest {
        val nodeIdentifier = Some.nodeIdentifier
        coEvery {
            primaryDataSource.fetchBankFromValidator(nodeIdentifier)
        } returns Outcome.Success(Mocks.bankFromValidator())

        val result = repository.bankFromValidator(nodeIdentifier)

        coVerify { primaryDataSource.fetchBankFromValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Success<BankFromValidator>>()
    }

    @Test
    fun `verify banks from validator is error`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchBanksFromValidator(PaginationOptions(0, 20))
        } returns Outcome.Error("", IOException())

        repository.banksFromValidator(0, 20) should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify banks from validator is success`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchBanksFromValidator(PaginationOptions(0, 20))
        } returns Outcome.Success(Mocks.banksFromValidator())

        repository.banksFromValidator(0, 20) should beInstanceOf<Outcome.Success<BankFromValidatorList>>()
    }

    @Test
    fun `verify primary validator details is error`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchValidatorDetails()
        } returns Outcome.Error("", IOException())

        repository.validatorDetails() should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify primary validator detail is success`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchValidatorDetails()
        } returns Outcome.Success(Mocks.validatorDetails("PRIMARY_VALIDATOR"))

        repository.validatorDetails() should beInstanceOf<Outcome.Success<ValidatorDetails>>()
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
    fun `verify validators result is error`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchValidators(PaginationOptions(0, 20))
        } returns Outcome.Error("", IOException())

        // when
        val result = repository.validators(0, 20)

        // then
        coVerify { primaryDataSource.fetchValidators(PaginationOptions(0, 20)) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify list of accounts from primary validator is success outcome`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchAccountsFromValidator(pagination)
        } returns Outcome.Success(Mocks.accountsFromValidator())

        val result = repository.accountsFromValidator(0, 20)

        coVerify { primaryDataSource.fetchAccountsFromValidator(pagination) }
        result should beInstanceOf<Outcome.Success<AccountListValidator>>()
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
    fun `verify validators result is success outcome`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchValidators(
                PaginationOptions(
                    0,
                    20
                )
            )
        } returns Outcome.Success(Mocks.validators())

        // when
        val result = repository.validators(0, 20)

        // then
        coVerify { primaryDataSource.fetchValidators(PaginationOptions(0, 20)) }
        result should beInstanceOf<Outcome.Success<ValidatorList>>()
    }

    @Test
    fun `verify single validator is error outcome`() = runBlockingTest {
        coEvery { primaryDataSource.fetchValidator(any()) } returns Outcome.Error("", IOException())

        val nodeIdentifier = "someNodeIdentifier"

        // when
        val result = repository.validator(nodeIdentifier)

        // then
        coVerify { primaryDataSource.fetchValidator(nodeIdentifier) }
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

    @Test
    fun `verify single validator result is success outcome`() = runBlockingTest {
        coEvery { primaryDataSource.fetchValidator(any()) } returns Outcome.Success(Mocks.validator())

        val nodeIdentifier = "someNodeIdentifier"
        // when
        val result = repository.validator(nodeIdentifier)

        // then
        coVerify { primaryDataSource.fetchValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Success<Validator>>()
    }

    @Test
    fun `verify confirmation blocks is success outcome`() = runBlockingTest {
        coEvery {
            primaryDataSource.fetchConfirmationBlocks(Some.blockIdentifier)
        } returns Outcome.Success(Mocks.confirmationBlocks())

        val result = repository.confirmationBlocks(Some.blockIdentifier)

        coVerify { primaryDataSource.fetchConfirmationBlocks(Some.blockIdentifier) }
        result should beInstanceOf<Outcome.Success<ConfirmationBlocks>>()
    }

    @Test
    fun `verify confirmation blocks is error outcome`() = runBlockingTest {
        coEvery { primaryDataSource.fetchConfirmationBlocks(Some.blockIdentifier) } returns Outcome.Error(
            "An error occurred",
            IOException()
        )

        val result = repository.confirmationBlocks(Some.blockIdentifier)

        coVerify { primaryDataSource.fetchConfirmationBlocks(Some.blockIdentifier) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify send bank block returns success outcome`() = runBlockingTest {
        val postRequest = Mocks.bankBlockRequest()
        val response = Mocks.bankBlock()
        coEvery {
            primaryDataSource.sendBankBlock(postRequest)
        } returns Outcome.Success(response)

        val result = repository.sendBankBlock(postRequest)

        coVerify { primaryDataSource.sendBankBlock(postRequest) }
        result should beInstanceOf<Outcome.Success<BankBlock>>()
    }

    @Test
    fun `verify send bank block returns error outcome`() = runBlockingTest {
        val postRequest = Mocks.bankBlockRequest()
        coEvery {
            primaryDataSource.sendBankBlock(postRequest)
        } returns Outcome.Error("Failed to send bank block", IOException())

        val result = repository.sendBankBlock(postRequest)

        coVerify { primaryDataSource.sendBankBlock(postRequest) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify connection request is success outcome`() = runBlockingTest {
        val connectionRequest = Mocks.connectionRequest()
        coEvery {
            primaryDataSource.sendConnectionRequest(connectionRequest)
        } returns Outcome.Success("Successfully sent connection requests")

        val result = repository.sendConnectionRequests(connectionRequest)

        coVerify { primaryDataSource.sendConnectionRequest(connectionRequest) }
        result should beInstanceOf<Outcome.Success<String>>()
    }

    @Test
    fun `verify connection request is error outcome`() = runBlockingTest {
        val connectionRequest = Mocks.connectionRequest()
        val message = "Could not send connection request"

        coEvery {
            primaryDataSource.sendConnectionRequest(connectionRequest)
        } returns Outcome.Error(message, IOException())

        val result = repository.sendConnectionRequests(connectionRequest)

        coVerify { primaryDataSource.sendConnectionRequest(connectionRequest) }
        result should beInstanceOf<Outcome.Error>()
    }
}
