package com.thenewboston.api.bankapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PatchDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.data.dto.bankapi.accountdto.response.Account
import com.thenewboston.data.dto.bankapi.accountdto.response.AccountList
import com.thenewboston.data.dto.bankapi.blockdto.Block
import com.thenewboston.data.dto.bankapi.blockdto.BlockList
import com.thenewboston.data.dto.bankapi.blockdto.request.PostBlockRequest
import com.thenewboston.data.dto.bankapi.clean.request.PostCleanRequest
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.data.dto.common.request.UpdateTrustRequest
import com.thenewboston.data.dto.common.response.Bank
import com.thenewboston.data.dto.common.request.ConnectionRequest
import com.thenewboston.data.dto.bankapi.crawl.request.PostCrawlRequest
import com.thenewboston.data.dto.bankapi.crawl.response.Crawl
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlock
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlockList
import com.thenewboston.data.dto.bankapi.invalidblockdto.request.PostInvalidBlockRequest
import com.thenewboston.data.dto.bankapi.upgradenoticedto.UpgradeNoticeRequest
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request.PostConfirmationServicesRequest
import com.thenewboston.data.dto.common.response.Validator
import com.thenewboston.data.dto.common.response.ValidatorList
import com.thenewboston.utils.PaginationOptions
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class BankDataSource @Inject constructor(
    private val getDataSource: GetDataSource,
    private val patchDataSource: PatchDataSource,
    private val postDataSource: PostDataSource
) {

    suspend fun fetchBanks(pagination: PaginationOptions) = makeApiCall(
        call = { getDataSource.banks(pagination) },
        errorMessage = "Failed to retrieve banks"
    )

    suspend fun fetchBankDetails() = makeApiCall(
        call = { getDataSource.bankDetail() },
        errorMessage = "Failed to retrieve bank details"
    )

    suspend fun fetchBankTransactions(pagination: PaginationOptions) = makeApiCall(
        call = { getDataSource.bankTransactions(pagination) },
        errorMessage = "Failed to retrieve bank transactions"
    )

    suspend fun fetchValidators(pagination: PaginationOptions): Outcome<ValidatorList> = makeApiCall(
        call = { getDataSource.validators(pagination) },
        errorMessage = "Could not fetch list of validators"
    )

    suspend fun fetchValidator(nodeIdentifier: String): Outcome<Validator> = makeApiCall(
        call = { getDataSource.validator(nodeIdentifier) },
        errorMessage = "Could not fetch validator with NID $nodeIdentifier"
    )

    suspend fun fetchAccounts(pagination: PaginationOptions): Outcome<AccountList> = makeApiCall(
        call = { getDataSource.accounts(pagination) },
        errorMessage = "Could not fetch list of accounts"
    )

    suspend fun fetchBlocks(pagination: PaginationOptions): Outcome<BlockList> = makeApiCall(
        call = { getDataSource.blocks(pagination) },
        errorMessage = "Could not fetch list of blocks"
    )

    suspend fun updateAccountTrust(accountNumber: String, request: UpdateTrustRequest): Outcome<Account> = makeApiCall(
        call = { patchDataSource.doUpdateAccount(accountNumber, request) },
        errorMessage = "Could not update trust level of given account"
    )

    suspend fun updateBankTrust(request: UpdateTrustRequest): Outcome<Bank> = makeApiCall(
        call = { patchDataSource.doUpdateBankTrust(request) },
        errorMessage = "Could not send bank trust for ${request.nodeIdentifier}"
    )

    suspend fun fetchInvalidBlocks(pagination: PaginationOptions): Outcome<InvalidBlockList> = makeApiCall(
        call = { getDataSource.invalidBlocks(pagination) },
        errorMessage = "Could not fetch list of invalid blocks"
    )

    suspend fun sendInvalidBlock(request: PostInvalidBlockRequest): Outcome<InvalidBlock> = makeApiCall(
        call = { patchDataSource.doSendInvalidBlock(request) },
        errorMessage = "An error occurred while sending invalid block"
    )

    suspend fun sendBlock(request: PostBlockRequest): Outcome<Block> = makeApiCall(
        call = { patchDataSource.doSendBlock(request) },
        errorMessage = "An error occurred while sending the block"
    )

    suspend fun fetchValidatorConfirmationServices(pagination: PaginationOptions) = makeApiCall(
        call = { getDataSource.validatorConfirmationServices(pagination) },
        errorMessage = "An error occurred while fetching validator confirmation services"
    )

    suspend fun sendValidatorConfirmationServices(request: PostConfirmationServicesRequest) = makeApiCall(
        call = { postDataSource.doSendConfirmationServices(request) },
        errorMessage = "An error occurred while sending validator confirmation services"
    )

    suspend fun sendUpgradeNotice(request: UpgradeNoticeRequest) = makeApiCall(
        call = { postDataSource.doSendUpgradeNotice(request) },
        errorMessage = "An error occurred while sending upgrade notice"
    )

    suspend fun fetchClean() = makeApiCall(
        call = { getDataSource.clean() },
        errorMessage = "Failed to update the network"
    )

    suspend fun fetchCrawl() = makeApiCall(
        call = { getDataSource.crawl() },
        errorMessage = "An error occurred while sending crawl request"
    )

    suspend fun sendClean(request: PostCleanRequest): Outcome<Clean> = makeApiCall(
        call = { postDataSource.doSendClean(request) },
        errorMessage = "An error occurred while sending the clean request"
    )

    suspend fun sendConnectionRequests(request: ConnectionRequest) = makeApiCall(
        call = { postDataSource.doSendConnectionRequests(request) },
        errorMessage = "An error occurred while sending connection requests"
    )

    suspend fun sendCrawl(request: PostCrawlRequest): Outcome<Crawl> = makeApiCall(
        call = { postDataSource.doSendCrawl(request) },
        errorMessage = "An error occurred while sending the crawl request"
    )
}
