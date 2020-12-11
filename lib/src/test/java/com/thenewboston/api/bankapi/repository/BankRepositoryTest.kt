package com.thenewboston.api.bankapi.repository

import com.thenewboston.api.bankapi.datasource.BankDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.accountdto.AccountList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankTrustResponse
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.banktransactiondto.BlockList
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorList
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
import javax.xml.validation.Validator

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

        repository.banks() should beInstanceOf<Outcome.Success<BankList>>()
    }

    @Test
    fun `verify detail result is error`() = runBlockingTest {
        coEvery {
            bankDataSource.fetchBankDetails()
        } returns Outcome.Error("", IOException())

        repository.bankDetail() should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify detail result is success`() = runBlockingTest {
        coEvery {
            bankDataSource.fetchBankDetails()
        } returns Outcome.Success(Mocks.bankDetails())

        repository.bankDetail() should beInstanceOf<Outcome.Success<BankDetails>>()
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

        repository.bankTransactions() should beInstanceOf<Outcome.Success<BankTransactionList>>()
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
        result should beInstanceOf<Outcome.Success<ValidatorList>>()
    }

    @Test
    fun `verify single validator result is success outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchValidator(any()) } returns Outcome.Success(Mocks.validator())

        val nodeIdentifier = "someNodeIdentifier"

        // when
        val result = repository.validator(nodeIdentifier)

        // then
        coVerify { bankDataSource.fetchValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Success<Validator>>()
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
        result should beInstanceOf<Outcome.Success<AccountList>>()
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
        result should beInstanceOf<Outcome.Success<BlockList>>()
    }

    @Test
    fun `verify send bank trust is success outcome`() = runBlockingTest {
        val response = Mocks.bankTrustResponse()
        coEvery { bankDataSource.sendBankTrust(Mocks.bankTrustRequest()) } returns Outcome.Success(response)

        // when
        val result = repository.sendBankTrust(Mocks.bankTrustRequest())

        // then
        coVerify { bankDataSource.sendBankTrust(Mocks.bankTrustRequest()) }
        result should beInstanceOf<Outcome.Success<BankTrustResponse>>()
    }

    @Test
    fun `verify send bank trust is error outcome`() = runBlockingTest {
        coEvery { bankDataSource.sendBankTrust(Mocks.bankTrustRequest()) } returns Outcome.Error("Failed to send bank trust", IOException())

        // when
        val result = repository.sendBankTrust(Mocks.bankTrustRequest())

        // then
        coVerify { bankDataSource.sendBankTrust(Mocks.bankTrustRequest()) }
        result should beInstanceOf<Outcome.Error>()
    }
}
