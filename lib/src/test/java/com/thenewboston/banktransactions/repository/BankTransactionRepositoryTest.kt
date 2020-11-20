package com.thenewboston.banktransactions.repository

import com.thenewboston.banktransactions.datasource.BankTransactionsDataSource
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

class BankTransactionRepositoryTest {

    @MockK
    lateinit var bankTransactionDataSource: BankTransactionsDataSource

    private lateinit var repository: BankTransactionRepository

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)

        repository = BankTransactionRepository(bankTransactionDataSource)
    }

    @Test
    fun `verify result is error`() = runBlockingTest {
        coEvery { bankTransactionDataSource.fetchBankTransactions() } returns
            Outcome.Error("", IOException())

        assertTrue(repository.bankTransactions() is Outcome.Error)
    }

    @Test
    fun `verify result is success`() = runBlockingTest {
        coEvery { bankTransactionDataSource.fetchBankTransactions() } returns
            Outcome.Success(Mocks.bankTransactions())

        assertTrue(repository.bankTransactions() is Outcome.Success)
    }
}
