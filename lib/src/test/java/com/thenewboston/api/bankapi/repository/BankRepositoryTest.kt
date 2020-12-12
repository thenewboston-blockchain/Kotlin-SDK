package com.thenewboston.api.bankapi.repository

import com.thenewboston.api.bankapi.datasource.BankDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.accountdto.response.Account
import com.thenewboston.data.dto.bankapi.accountdto.response.AccountList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankTrustResponse
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.blockdto.BlockList
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlock
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorList
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
import org.junit.jupiter.api.Test
import java.io.IOException
import javax.xml.validation.Validator
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@ExperimentalCoroutinesApi
@KtorExperimentalAPI
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankRepositoryTest {

    @MockK
    lateinit var bankDataSource: BankDataSource

    private lateinit var repository: BankRepository

    @BeforeAll
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
    fun `verify update bank trust returns success outcome`() = runBlockingTest {
        val response = Mocks.bankTrustResponse()
        coEvery { bankDataSource.updateBankTrust(Mocks.trustRequest()) } returns Outcome.Success(
            response
        )

        // when
        val result = repository.updateBankTrust(Mocks.trustRequest())

        // then
        coVerify { bankDataSource.updateBankTrust(Mocks.trustRequest()) }
        result should beInstanceOf<Outcome.Success<BankTrustResponse>>()
    }

    @Test
    fun `verify update bank trust returns error outcome`() = runBlockingTest {
        coEvery { bankDataSource.updateBankTrust(Mocks.trustRequest()) } returns Outcome.Error(
            "Failed to send bank trust",
            IOException()
        )

        // when
        val result = repository.updateBankTrust(Mocks.trustRequest())

        // then
        coVerify { bankDataSource.updateBankTrust(Mocks.trustRequest()) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify update account trust returns success outcome`() = runBlockingTest {
        // given
        val accountNumber = Some.accountNumber
        val request = Mocks.trustRequest()
        val response = Mocks.account(42.0)
        coEvery {
            bankDataSource.updateAccountTrust(
                accountNumber,
                request
            )
        } returns Outcome.Success(response)

        // when
        val result = repository.updateAccountTrust(accountNumber, request)

        // then
        coVerify { bankDataSource.updateAccountTrust(accountNumber, request) }
        result should beInstanceOf<Outcome.Success<Account>>()
    }

    @Test
    fun `verify update account trust returns error outcome`() = runBlockingTest {
        // given
        val accountNumber = Some.accountNumber
        val trustRequest = Mocks.trustRequest()
        coEvery {
            bankDataSource.updateAccountTrust(
                accountNumber,
                trustRequest
            )
        } returns Outcome.Error("Failed to send bank trust", IOException())

        // when
        val result = repository.updateAccountTrust(accountNumber, trustRequest)

        // then
        coVerify { bankDataSource.updateAccountTrust(accountNumber, trustRequest) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify list of invalid blocks is error outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchInvalidBlocks() } returns Outcome.Error("", IOException())

        // when
        val result = repository.invalidBlocks()

        // then
        coVerify { bankDataSource.fetchInvalidBlocks() }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify list of invalid blocks is success outcome`() = runBlockingTest {
        coEvery { bankDataSource.fetchInvalidBlocks() } returns Outcome
            .Success(Mocks.invalidBlocks())

        // when
        val result = repository.invalidBlocks()

        // then
        coVerify { bankDataSource.fetchInvalidBlocks() }
        result should beInstanceOf<Outcome.Success<*>>()
    }

    @Test
    fun `verify send invalid block returns success outcome`() = runBlockingTest {
        val response = Mocks.invalidBlock()
        coEvery { bankDataSource.sendInvalidBlock(any()) } returns Outcome.Success(response)
        val postRequest = Mocks.postInvalidBlockRequest()

        // when
        val result = repository.sendInvalidBlock(postRequest)

        // then
        coVerify { bankDataSource.sendInvalidBlock(postRequest) }
        result should beInstanceOf<Outcome.Success<InvalidBlock>>()
    }

    @Test
    fun `verify send invalid block returns error outcome`() = runBlockingTest {
        val error = Outcome.Error("Failed to send invalid block", IOException())
        val request = Mocks.postInvalidBlockRequest()
        coEvery { bankDataSource.sendInvalidBlock(request) } returns error

        // when
        val result = repository.sendInvalidBlock(request)

        // then
        coVerify { bankDataSource.sendInvalidBlock(request) }
        result should beInstanceOf<Outcome.Error>()
    }
}
