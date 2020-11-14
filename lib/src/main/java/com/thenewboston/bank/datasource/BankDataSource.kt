package com.thenewboston.bank.datasource

import com.thenewboston.bank.model.Bank
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import io.ktor.client.request.*
import io.ktor.utils.io.errors.*
import javax.inject.Inject

class BankDataSource @Inject constructor(private val networkClient: NetworkClient) {

    suspend fun fetchBanks() = makeApiCall(
        call = { banks() },
        errorMessage = "Failed to retrieve banks"
    )

    private suspend fun banks(): Outcome<Bank> {
        val result = networkClient.client.get<Bank>("/banks")

        return when {
            result.results.isNullOrEmpty() -> Outcome.Error("Error fetching banks", IOException())
            else -> Outcome.Success(result)
        }
    }
}