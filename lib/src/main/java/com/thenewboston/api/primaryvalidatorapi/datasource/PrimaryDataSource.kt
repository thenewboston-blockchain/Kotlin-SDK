package com.thenewboston.api.primaryvalidatorapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.utils.PaginationOptions
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class PrimaryDataSource @Inject constructor(
    private val getDataSource: GetDataSource,
    private val postDataSource: PostDataSource
) {

    suspend fun fetchPrimaryValidatorDetails() = makeApiCall(
        call = { getDataSource.primaryValidatorDetails() },
        errorMessage = "Failed to retrieve primary validator details"
    )

    suspend fun fetchAccountsFromValidator(pagination: PaginationOptions) = makeApiCall(
        call = { getDataSource.accountsFromValidator(pagination) },
        errorMessage = "Failed to retrieve accounts from primary validator"
    )

    suspend fun fetchAccountBalance(accountNumber: String) = makeApiCall(
        call = { getDataSource.accountBalance(accountNumber) },
        errorMessage = "Failed to retrieve account balance from primary validator"
    )

    suspend fun fetchAccountBalanceLock(accountNumber: String) = makeApiCall(
        call = { getDataSource.accountBalanceLock(accountNumber) },
        errorMessage = "Failed to retrieve account balance lock from primary validator"
    )
}
