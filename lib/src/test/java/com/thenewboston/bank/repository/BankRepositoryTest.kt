package com.thenewboston.bank.repository

import com.thenewboston.bank.datasource.BankDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.utils.Mocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

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
    fun `verify result is error`() = runBlockingTest {
        coEvery { bankDataSource.fetchBanks() } returns Outcome.Error("", IOException())

        assertTrue(repository.banks() is Outcome.Error)
    }

    @Test
    fun `verify result is success`() = runBlockingTest {
        coEvery { bankDataSource.fetchBanks() } returns Outcome.Success(Mocks.banks())

        assertTrue(repository.banks() is Outcome.Success)
    }
}