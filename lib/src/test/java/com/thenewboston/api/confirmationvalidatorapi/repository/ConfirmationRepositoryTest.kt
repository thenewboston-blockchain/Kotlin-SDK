package com.thenewboston.api.confirmationvalidatorapi.repository

import com.thenewboston.api.confirmationvalidatorapi.datasource.ConfirmationDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.data.dto.bankapi.crawl.response.Crawl
import com.thenewboston.data.dto.common.response.*
import com.thenewboston.data.dto.common.response.ConfirmationServicesList
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
    fun `verify fetch bank confirmation services returns success outcome`() = runBlockingTest {
        val pagination = PaginationOptions(0, 20)
        val value = Mocks.bankConfirmationServicesList()
        coEvery { dataSource.fetchBankConfirmationServices(pagination) } returns Outcome.Success(value)

        val result = repository.bankConfirmationServices(0, 20)

        coVerify { dataSource.fetchBankConfirmationServices(pagination) }
        result should beInstanceOf<Outcome.Success<ConfirmationServicesList>>()
    }

    @Test
    fun `verify fetch bank confirmation services returns error outcome`() = runBlockingTest {
        coEvery { dataSource.fetchBankConfirmationServices(PaginationOptions(0, 20)) } returns Outcome.Error(
            "Failed to fetch bank confirmation services",
            IOException()
        )

        // when
        val result = repository.bankConfirmationServices(0, 20)

        // then
        coVerify { dataSource.fetchBankConfirmationServices(PaginationOptions(0, 20)) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify single validator result is success outcome`() = runBlockingTest {
        coEvery { dataSource.fetchValidator(any()) } returns Outcome.Success(Mocks.validator())

        val nodeIdentifier = "someNodeIdentifier"

        val result = repository.validator(nodeIdentifier)

        coVerify { dataSource.fetchValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Success<Validator>>()
    }

    @Test
    fun `verify single validator is error outcome`() = runBlockingTest {
        coEvery { dataSource.fetchValidator(any()) } returns Outcome.Error("", IOException())

        val nodeIdentifier = "someNodeIdentifier"

        val result = repository.validator(nodeIdentifier)

        coVerify { dataSource.fetchValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify validators result is success outcome`() = runBlockingTest {
        coEvery {
            dataSource.fetchValidators(PaginationOptions(0, 20))
        } returns Outcome.Success(Mocks.validators())

        val result = repository.validators(0, 20)

        coVerify { dataSource.fetchValidators(PaginationOptions(0, 20)) }
        result should beInstanceOf<Outcome.Success<ValidatorList>>()
    }

    @Test
    fun `verify validators result is error`() = runBlockingTest {
        coEvery {
            dataSource.fetchValidators(PaginationOptions(0, 20))
        } returns Outcome.Error("", IOException())

        // when
        val result = repository.validators(0, 20)

        // then
        coVerify { dataSource.fetchValidators(PaginationOptions(0, 20)) }
        result should beInstanceOf<Outcome.Error>()
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
    fun `verify valid confirmation blocks is success outcome`() = runBlockingTest {
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
    fun `verify queued confirmation blocks is success outcome`() = runBlockingTest {
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

    @Test
    fun `verify send confirmation blocks is success outcome`() = runBlockingTest {
        val request = Mocks.confirmationBlocks()
        coEvery {
            dataSource.sendConfirmationBlocks(request)
        } returns Outcome.Success(Mocks.confirmationBlockMessage())

        val result = repository.sendConfirmationBlocks(request)

        coVerify { dataSource.sendConfirmationBlocks(request) }
        result should beInstanceOf<Outcome.Success<ConfirmationBlocks>>()
    }

    @Test
    fun `verify send confirmation blocks is error outcome`() = runBlockingTest {
        val request = Mocks.confirmationBlocks()

        coEvery { dataSource.sendConfirmationBlocks(request) } returns Outcome.Error(
            "An error occurred while sending confirmation blocks",
            IOException()
        )

        val result = repository.sendConfirmationBlocks(request)

        coVerify { dataSource.sendConfirmationBlocks(request) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify crawl result returns success outcome`() = runBlockingTest {
        coEvery { dataSource.fetchCrawl() } returns Outcome.Success(Mocks.crawlSuccess())

        repository.crawl() should beInstanceOf<Outcome.Success<Crawl>>()
    }

    @Test
    fun `verify crawl result returns error outcome`() = runBlockingTest {
        val message = "The network crawling process is not successful"
        coEvery { dataSource.fetchCrawl() } returns Outcome.Error(message, IOException())

        repository.crawl() should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify send crawl returns success outcome`() = runBlockingTest {
        val response = Mocks.crawlSuccess()
        coEvery { dataSource.sendCrawl(any()) } returns Outcome.Success(response)
        val postRequest = Mocks.postCrawlRequest()

        // when
        val result = repository.sendCrawl(postRequest)

        // then
        coVerify { dataSource.sendCrawl(postRequest) }
        result should beInstanceOf<Outcome.Success<Crawl>>()
    }

    @Test
    fun `verify send crawl returns error outcome`() = runBlockingTest {
        val error = Outcome.Error("An error occurred while sending the crawl request", IOException())
        val request = Mocks.postCrawlRequest()
        coEvery { dataSource.sendCrawl(request) } returns error

        // when
        val result = repository.sendCrawl(request)

        // then
        coVerify { dataSource.sendCrawl(request) }
        result should beInstanceOf<Outcome.Error>()
    }
}
