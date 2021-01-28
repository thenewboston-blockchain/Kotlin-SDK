package com.thenewboston.api.primaryvalidatorapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.utils.PaginationOptions
import com.thenewboston.utils.ErrorMessages

import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class PrimaryDataSource @Inject constructor(
    private val getDataSource: GetDataSource,
    private val postDataSource: PostDataSource
) {

    suspend fun fetchBankFromValidator(nodeIdentifier: String) = makeApiCall(
        call = { getDataSource.bankFromValidator(nodeIdentifier) },
        errorMessage = "Failed to retrieve bank from validator"
    )

    suspend fun fetchBanksFromValidator(pagination: PaginationOptions) = makeApiCall(
        call = { getDataSource.banksFromValidator(pagination) },
        errorMessage = ErrorMessages.EMPTY_LIST_MESSAGE
    )

    suspend fun fetchPrimaryValidatorDetails() = makeApiCall(
        call = { getDataSource.primaryValidatorDetails() },
        errorMessage = "Failed to retrieve primary validator details"
    )
}
