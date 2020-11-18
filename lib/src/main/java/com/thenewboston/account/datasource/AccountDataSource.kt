package com.thenewboston.account.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.data.dto.bankapi.accountdto.AccountListDTO
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI
import javax.inject.Inject

@KtorExperimentalAPI
class AccountDataSource @Inject constructor(private val networkClient: NetworkClient){

    suspend fun fetchAccounts(): Outcome<AccountListDTO> = makeApiCall(
        call = { accounts() },
        errorMessage = "Failed to retrieve accounts"
    )

    private suspend fun accounts(): Outcome<AccountListDTO> {
        val result = networkClient.client.get<AccountListDTO>("/accounts")

        return  when {
            result.accounts.isNullOrEmpty() -> Outcome.Error("Error fetching accounts", null)
            else -> Outcome.Success(result)
        }
    }
}
