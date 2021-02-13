package com.thenewboston.api.confirmationvalidatorapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.utils.PaginationOptions
import javax.inject.Inject

class ConfirmationDataSource @Inject constructor(private val getDataSource: GetDataSource) {

    suspend fun fetchAccounts(paginationOptions: PaginationOptions) = makeApiCall(
        call = { getDataSource.accountsFromValidator(paginationOptions) },
        errorMessage = "Error while retrieving accounts"
    )

    suspend fun fetchValidatorDetails() = makeApiCall(
        call = { getDataSource.validatorDetails() },
        errorMessage = "Failed to retrieve confirmation validator details"
    )
}
