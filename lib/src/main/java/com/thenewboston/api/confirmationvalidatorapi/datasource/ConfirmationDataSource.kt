package com.thenewboston.api.confirmationvalidatorapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.data.dto.bankapi.clean.request.PostCleanRequest
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.data.dto.bankapi.crawl.request.PostCrawlRequest
import com.thenewboston.data.dto.bankapi.crawl.response.Crawl
import com.thenewboston.data.dto.common.response.ConfirmationBlockMessage
import com.thenewboston.data.dto.common.response.ConfirmationBlocks
import com.thenewboston.data.dto.common.response.Validator
import com.thenewboston.data.dto.common.response.ValidatorList
import com.thenewboston.utils.ErrorMessages
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

    suspend fun fetchBankConfirmationServices(pagination: PaginationOptions) = makeApiCall(
        call = { getDataSource.bankConfirmationServices(pagination) },
        errorMessage = "An error occurred while fetching bank confirmation services"
    )

    suspend fun fetchValidator(nodeIdentifier: String): Outcome<Validator> = makeApiCall(
        call = { getDataSource.validator(nodeIdentifier) },
        errorMessage = "Could not fetch validator with NID $nodeIdentifier"
    )

    suspend fun fetchValidators(pagination: PaginationOptions): Outcome<ValidatorList> = makeApiCall(
        call = { getDataSource.validators(pagination) },
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

    suspend fun fetchValidConfirmationBlocks(blockIdentifier: String): Outcome<ConfirmationBlocks> = makeApiCall(
        call = { getDataSource.validConfirmationBlocks(blockIdentifier) },
        errorMessage = "Could not fetch valid confirmation blocks with block identifier $blockIdentifier"
    )

    suspend fun fetchQueuedConfirmationBlocks(blockIdentifier: String): Outcome<ConfirmationBlocks> = makeApiCall(
        call = { getDataSource.queuedConfirmationBlocks(blockIdentifier) },
        errorMessage = "Could not fetch queued confirmation blocks with block identifier $blockIdentifier"
    )

    suspend fun sendConfirmationBlocks(request: ConfirmationBlocks): Outcome<ConfirmationBlockMessage> = makeApiCall(
        call = { postDataSource.doSendConfirmationBlocks(request) },
        errorMessage = "An error occurred while sending confirmation blocks"
    )

    suspend fun fetchCrawl() = makeApiCall(
        call = { getDataSource.crawl() },
        errorMessage = "An error occurred while sending crawl request"
    )

    suspend fun sendCrawl(request: PostCrawlRequest): Outcome<Crawl> = makeApiCall(
        call = { postDataSource.doSendCrawl(request) },
        errorMessage = "An error occurred while sending the crawl request"
    )
}
