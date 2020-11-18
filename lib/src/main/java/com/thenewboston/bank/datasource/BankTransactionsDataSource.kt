package com.thenewboston.bank.datasource

import com.thenewboston.bank.model.BankTransactionList
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import io.ktor.client.request.*
import java.io.IOException
import javax.inject.Inject

class BankTransactionsDataSource @Inject constructor(private val networkClient: NetworkClient) {

    suspend fun fetchBankTransactions() = makeApiCall(
        call = { bankTransactions() },
        errorMessage = "Failed to retrieve bank transactions"
    )


    private suspend fun bankTransactions(): Outcome<BankTransactionList> {
        val result = networkClient.client.get<BankTransactionList>("/bank_transactions")

        return when {
            result.bankTransactions.isNullOrEmpty() -> Outcome.Error("Error bank transactions", IOException())
            else -> Outcome.Success(result)
        }
    }
}
