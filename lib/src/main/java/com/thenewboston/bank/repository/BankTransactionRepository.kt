package com.thenewboston.bank.repository

import com.thenewboston.bank.datasource.BankTransactionsDataSource
import com.thenewboston.bank.model.BankTransactionList
import com.thenewboston.common.http.Outcome
import javax.inject.Inject

class BankTransactionRepository @Inject constructor(
    private val dataSource: BankTransactionsDataSource
) {

    suspend fun bankTransactions(): Outcome<BankTransactionList> =
        dataSource.fetchBankTransactions()
}
