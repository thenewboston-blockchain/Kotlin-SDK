package com.thenewboston.api.confirmationvalidatorapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.utils.ErrorMessages
import com.thenewboston.data.dto.bankapi.clean.request.PostCleanRequest
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.utils.PaginationOptions
import javax.inject.Inject

class ConfirmationDataSource @Inject constructor(
    private val getDataSource: GetDataSource,
    private val postDataSource: PostDataSource

) {

    suspend fun fetchAccounts(paginationOptions: PaginationOptions) = makeApiCall(
        call = { getDataSource.accountsFromValidator(paginationOptions) },
        errorMessage = "Error while retrieving accounts"
    )

    suspend fun fetchValidatorDetails() = makeApiCall(
        call = { getDataSource.validatorDetails() },
        errorMessage = "Failed to retrieve confirmation validator details"
    )

    suspend fun fetchBankFromValidator(nodeIdentifier: String) = makeApiCall(
        call = { getDataSource.bankFromValidator(nodeIdentifier) },
        errorMessage = "Failed to retrieve bank from validator"
    )

    suspend fun fetchBanksFromValidator(paginationOptions: PaginationOptions) = makeApiCall(
        call = { getDataSource.banksFromValidator(paginationOptions) },
        errorMessage = ErrorMessages.EMPTY_LIST_MESSAGE
    )

    suspend fun fetchClean() = makeApiCall(
        call = { getDataSource.clean() },
        errorMessage = "Failed to update the network"
    )

    suspend fun sendClean(request: PostCleanRequest): Outcome<Clean> = makeApiCall(
        call = { postDataSource.doSendClean(request) },
        errorMessage = "An error occurred while sending the clean request"
    )
}
