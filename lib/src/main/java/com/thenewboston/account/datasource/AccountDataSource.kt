package com.thenewboston.account.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.data.dto.bankapi.accountdto.AccountListDTO
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI
import io.ktor.utils.io.errors.*
import javax.inject.Inject

@KtorExperimentalAPI
class AccountDataSource @Inject constructor(private val networkClient: NetworkClient) {

    suspend fun fetchAccounts(): Outcome<AccountListDTO> = makeApiCall(
        call = { accounts() },
        errorMessage = "Could not fetch list of accounts"
    )

    private suspend fun accounts(): Outcome<AccountListDTO> {
        val accounts = networkClient.client.get<AccountListDTO>("/accounts")

        return when {
            accounts.results.isNullOrEmpty() -> Outcome.Error(
                "Received null or empty list",
                IOException()
            )
            else -> Outcome.Success(accounts)
        }
    }
}
