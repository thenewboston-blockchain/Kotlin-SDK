package com.thenewboston.api.primaryvalidatorapi.repository

import com.thenewboston.api.primaryvalidatorapi.datasource.PrimaryDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.primaryvalidatorapi.accountdto.AccountBalance
import com.thenewboston.data.dto.primaryvalidatorapi.accountdto.AccountBalanceLock
import com.thenewboston.data.dto.primaryvalidatorapi.accountdto.AccountFromValidatorList
import com.thenewboston.data.dto.primaryvalidatorapi.configdto.PrimaryValidatorDetails
import com.thenewboston.utils.PaginationOptions
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class PrimaryRepository @Inject constructor(private val dataSource: PrimaryDataSource) {

    suspend fun primaryValidatorDetails(): Outcome<PrimaryValidatorDetails> =
        dataSource.fetchPrimaryValidatorDetails()

    suspend fun accountsFromValidator(offset: Int, limit: Int): Outcome<AccountFromValidatorList> =
        dataSource.fetchAccountsFromValidator(PaginationOptions(offset, limit))

    suspend fun accountBalance(accountNumber: String): Outcome<AccountBalance> =
        dataSource.fetchAccountBalance(accountNumber)

    suspend fun accountBalanceLock(accountNumber: String): Outcome<AccountBalanceLock> =
        dataSource.fetchAccountBalanceLock(accountNumber)
}
