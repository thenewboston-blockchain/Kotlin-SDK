package com.thenewboston.account.repository

import com.thenewboston.account.datasource.AccountDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.accountdto.AccountListDTO
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class AccountRepository @Inject constructor(private val dataSource: AccountDataSource) {

    suspend fun accounts(): Outcome<AccountListDTO> = dataSource.fetchAccounts()
}
