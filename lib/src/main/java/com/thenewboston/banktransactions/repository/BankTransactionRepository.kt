package com.thenewboston.banktransactions.repository

import com.thenewboston.banktransactions.datasource.BankTransactionsDataSource
import com.thenewboston.banktransactions.model.BankTransactionList
import com.thenewboston.common.http.Outcome
import javax.inject.Inject

class BankTransactionRepository @Inject constructor(
    private val dataSource: BankTransactionsDataSource
) {

    suspend fun bankTransactions(): Outcome<BankTransactionList> =
        dataSource.fetchBankTransactions()
}
