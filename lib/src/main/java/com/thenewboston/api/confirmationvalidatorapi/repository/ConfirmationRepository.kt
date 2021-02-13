package com.thenewboston.api.confirmationvalidatorapi.repository

import com.thenewboston.api.confirmationvalidatorapi.datasource.ConfirmationDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.clean.request.PostCleanRequest
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.data.dto.common.response.AccountListValidator
import com.thenewboston.utils.PaginationOptions
import javax.inject.Inject

class ConfirmationRepository @Inject constructor(private val dataSource: ConfirmationDataSource) {

    suspend fun accounts(offset: Int, limit: Int): Outcome<AccountListValidator> =
        dataSource.fetchAccounts(PaginationOptions(offset, limit))

    suspend fun sendClean(request: PostCleanRequest): Outcome<Clean> = dataSource.sendClean(request)
}
