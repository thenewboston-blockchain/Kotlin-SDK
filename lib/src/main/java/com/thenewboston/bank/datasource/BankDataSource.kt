package com.thenewboston.bank.datasource

import com.thenewboston.bank.model.BankDetails
import com.thenewboston.bank.model.BankList
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import com.thenewboston.common.http.makeApiCall
import io.ktor.client.request.*
import io.ktor.utils.io.errors.*
import javax.inject.Inject

class BankDataSource @Inject constructor(private val networkClient: NetworkClient) {

    suspend fun fetchBanks() = makeApiCall(
        call = { banks() },
        errorMessage = "Failed to retrieve banks"
    )

    private suspend fun banks(): Outcome<BankList> {
        val result = networkClient.defaultClient.get<BankList>("/banks")

        return when {
            result.banks.isNullOrEmpty() -> Outcome.Error("Error fetching banks", IOException())
            else -> Outcome.Success(result)
        }
    }

    suspend fun fetchBankDetails(bankConfig: BankConfig) = makeApiCall(
        call = { bankDetail(bankConfig) },
        errorMessage = "Failed to retrieve bank details"
    )

    private suspend fun bankDetail(bankConfig: BankConfig): Outcome<BankDetails> {
        val result = networkClient.newClient(
            ipAddress = bankConfig.ipAddress,
            port = bankConfig.port
        ).get<BankDetails>("/config")

        return Outcome.Success(result)
    }
}
