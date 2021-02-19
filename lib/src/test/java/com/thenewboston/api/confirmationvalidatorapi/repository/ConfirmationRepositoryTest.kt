package com.thenewboston.api.confirmationvalidatorapi.repository

import com.thenewboston.api.confirmationvalidatorapi.datasource.ConfirmationDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.data.dto.common.response.AccountListValidator
import com.thenewboston.data.dto.common.response.ConfirmationBlocks
import com.thenewboston.data.dto.common.response.ValidatorDetails
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
class ConfirmationRepositoryTest {

    @MockK
    lateinit var dataSource: ConfirmationDataSource

    lateinit var repository: ConfirmationRepository

    @BeforeAll
    fun setup() {
        MockKAnnotations.init(this)

        repository = ConfirmationRepository(dataSource)
    }

    @Test
    fun `verify accounts returns success outcome`() = runBlockingTest {
        val pagination = Mocks.paginationOptionsDefault()
        coEvery { dataSource.fetchAccounts(pagination) } returns Outcome.Success(Mocks.accountsFromValidator(pagination))

        val response = repository.accounts(0, 20)
        coVerify { dataSource.fetchAccounts(pagination) }
        response should beInstanceOf<Outcome.Success<AccountListValidator>>()
    }

    @Test
    fun `verify accounts returns error outcome`() = runBlockingTest {
        val pagination = Mocks.paginationOptionsDefault()
        val message = "Error while retrieving accounts"
        coEvery { dataSource.fetchAccounts(pagination) } returns Outcome.Error(message, IOException())

        val response = repository.accounts(0, 20)
        coVerify { dataSource.fetchAccounts(pagination) }
        response should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify confirmation validator detail is success`() = runBlockingTest {
        coEvery {
            dataSource.fetchValidatorDetails()
        } returns Outcome.Success(Mocks.validatorDetails("CONFIRMATION_VALIDATOR"))

        repository.validatorDetails() should beInstanceOf<Outcome.Success<ValidatorDetails>>()
    }

    @Test
    fun `verify confirmation validator details is error`() = runBlockingTest {
        coEvery {
            dataSource.fetchValidatorDetails()
        } returns Outcome.Error("", IOException())

        repository.validatorDetails() should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify single bank from validator is error outcome`() = runBlockingTest {
        val nodeIdentifier = Some.nodeIdentifier
        coEvery { dataSource.fetchBankFromValidator(nodeIdentifier) } returns Outcome.Error("", IOException())

        val result = repository.bankFromValidator(nodeIdentifier)

        coVerify { dataSource.fetchBankFromValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify single bank from validator is success outcome`() = runBlockingTest {
        val nodeIdentifier = Some.nodeIdentifier
        coEvery {
            dataSource.fetchBankFromValidator(nodeIdentifier)
        } returns Outcome.Success(Mocks.bankFromValidator())

        val result = repository.bankFromValidator(nodeIdentifier)

        coVerify { dataSource.fetchBankFromValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Success<BankFromValidator>>()
    }

    @Test
    fun `verify banks from validator is error`() = runBlockingTest {
        coEvery {
            dataSource.fetchBanksFromValidator(PaginationOptions(0, 20))
        } returns Outcome.Error("", IOException())

        repository.banksFromValidator(0, 20) should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify banks from validator is success`() = runBlockingTest {
        coEvery {
            dataSource.fetchBanksFromValidator(PaginationOptions(0, 20))
        } returns Outcome.Success(Mocks.banksFromValidator())

        repository.banksFromValidator(0, 20) should beInstanceOf<Outcome.Success<BankFromValidatorList>>()
    }

    @Test
    fun `verify clean result returns success outcome`() = runBlockingTest {
        coEvery { dataSource.fetchClean() } returns Outcome.Success(Mocks.cleanSuccess())

        repository.clean() should beInstanceOf<Outcome.Success<Clean>>()
    }

    @Test
    fun `verify clean result returns error outcome`() = runBlockingTest {
        coEvery {
            dataSource.fetchClean()
        } returns Outcome.Error("The network clean process is not successful", IOException())

        repository.clean() should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify send clean returns success outcome`() = runBlockingTest {
        val response = Mocks.cleanSuccess()
        coEvery { dataSource.sendClean(any()) } returns Outcome.Success(response)
        val postRequest = Mocks.postCleanRequest()

        // when
        val result = repository.sendClean(postRequest)

        // then
        coVerify { dataSource.sendClean(postRequest) }
        result should beInstanceOf<Outcome.Success<Clean>>()
    }

    @Test
    fun `verify send clean returns error outcome`() = runBlockingTest {
        val error = Outcome.Error("An error occurred while sending the clean request", IOException())
        val request = Mocks.postCleanRequest()
        coEvery { dataSource.sendClean(request) } returns error

        // when
        val result = repository.sendClean(request)

        // then
        coVerify { dataSource.sendClean(request) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify valid confirmations blocks is success outcome`() = runBlockingTest {
        coEvery {
            dataSource.fetchValidConfirmationBlocks(Some.blockIdentifier)
        } returns Outcome.Success(Mocks.confirmationBlocks())

        val result = repository.validConfirmationBlocks(Some.blockIdentifier)

        coVerify { dataSource.fetchValidConfirmationBlocks(Some.blockIdentifier) }
        result should beInstanceOf<Outcome.Success<ConfirmationBlocks>>()
    }

    @Test
    fun `verify valid confirmation blocks is error outcome`() = runBlockingTest {
        coEvery { dataSource.fetchValidConfirmationBlocks(Some.blockIdentifier) } returns Outcome.Error(
            "An error occurred while fetching valid confirmation blocks",
            IOException()
        )

        val result = repository.validConfirmationBlocks(Some.blockIdentifier)

        coVerify { dataSource.fetchValidConfirmationBlocks(Some.blockIdentifier) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify queued confirmations blocks is success outcome`() = runBlockingTest {
        coEvery {
            dataSource.fetchQueuedConfirmationBlocks(Some.blockIdentifier)
        } returns Outcome.Success(Mocks.confirmationBlocks())

        val result = repository.queuedConfirmationBlocks(Some.blockIdentifier)

        coVerify { dataSource.fetchQueuedConfirmationBlocks(Some.blockIdentifier) }
        result should beInstanceOf<Outcome.Success<ConfirmationBlocks>>()
    }

    @Test
    fun `verify queued confirmation blocks is error outcome`() = runBlockingTest {
        coEvery { dataSource.fetchQueuedConfirmationBlocks(Some.blockIdentifier) } returns Outcome.Error(
            "An error occurred while fetching queued confirmation blocks",
            IOException()
        )

        val result = repository.queuedConfirmationBlocks(Some.blockIdentifier)

        coVerify { dataSource.fetchQueuedConfirmationBlocks(Some.blockIdentifier) }
        result should beInstanceOf<Outcome.Error>()
    }
}
