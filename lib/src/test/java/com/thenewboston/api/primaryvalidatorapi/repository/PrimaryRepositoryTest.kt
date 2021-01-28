package com.thenewboston.api.primaryvalidatorapi.repository

import com.thenewboston.api.primaryvalidatorapi.datasource.PrimaryDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.primaryvalidatorapi.configdto.PrimaryValidatorDetails
import com.thenewboston.data.dto.common.response.Validator
import com.thenewboston.data.dto.common.response.ValidatorList
import com.thenewboston.utils.Mocks
import com.thenewboston.utils.PaginationOptions
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
    fun `verify validators result is error`() = runBlockingTest {
        coEvery { primaryDataSource.fetchValidators(PaginationOptions(0, 20)) } returns Outcome.Error("", IOException())

        // when
        val result = repository.validators(0, 20)

        // then
        coVerify { primaryDataSource.fetchValidators(PaginationOptions(0, 20)) }
        result should beInstanceOf<Outcome.Error>()
    }

    @Test
    fun `verify validators result is success outcome`() = runBlockingTest {
        coEvery { primaryDataSource.fetchValidators(PaginationOptions(0, 20)) } returns Outcome.Success(Mocks.validators())

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
    fun `verify single validator result is success outcome`() = runBlockingTest {
        coEvery { primaryDataSource.fetchValidator(any()) } returns Outcome.Success(Mocks.validator())

        val nodeIdentifier = "someNodeIdentifier"

        // when
        val result = repository.validator(nodeIdentifier)

        // then
        coVerify { primaryDataSource.fetchValidator(nodeIdentifier) }
        result should beInstanceOf<Outcome.Success<Validator>>()
    }
}
