package com.thenewboston.api.primaryvalidatorapi.repository

import com.thenewboston.api.primaryvalidatorapi.datasource.PrimaryDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.primaryvalidatorapi.configdto.PrimaryValidatorDetails
import com.thenewboston.utils.Mocks
import io.kotest.matchers.should
import io.kotest.matchers.types.beInstanceOf
import io.ktor.util.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
}
