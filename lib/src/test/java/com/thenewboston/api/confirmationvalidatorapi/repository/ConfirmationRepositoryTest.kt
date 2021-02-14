package com.thenewboston.api.confirmationvalidatorapi.repository

import com.thenewboston.api.confirmationvalidatorapi.datasource.ConfirmationDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.common.response.AccountListValidator
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
}
