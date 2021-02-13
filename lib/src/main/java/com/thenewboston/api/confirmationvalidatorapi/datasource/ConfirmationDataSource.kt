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

    suspend fun fetchClean() = makeApiCall(
        call = { getDataSource.clean() },
        errorMessage = "Failed to update the network"
    )
}
