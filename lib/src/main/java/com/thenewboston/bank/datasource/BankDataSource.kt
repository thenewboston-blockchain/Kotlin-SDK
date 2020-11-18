package com.thenewboston.bank.datasource

import com.thenewboston.bank.model.BankList
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import io.ktor.client.request.*
import io.ktor.utils.io.errors.*
import javax.inject.Inject

class BankDataSource @Inject constructor(private val networkClient: NetworkClient) {

    suspend fun fetchBankTransactions() = makeApiCall(
        call = { bankTransactions() },
        errorMessage = "Failed to retrieve banks"
    )

    private suspend fun bankTransactions(): Outcome<BankList> {
        val result = networkClient.client.get<BankList>("/banks")

        return when {
            result.banks.isNullOrEmpty() -> Outcome.Error("Error fetching banks", IOException())
            else -> Outcome.Success(result)
        }
    }
}
