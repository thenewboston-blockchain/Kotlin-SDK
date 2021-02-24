package com.thenewboston.api.bankapi.repository

import com.thenewboston.api.bankapi.datasource.BankDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.accountdto.response.Account
import com.thenewboston.data.dto.bankapi.accountdto.response.AccountList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankList
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.blockdto.Block
import com.thenewboston.data.dto.bankapi.blockdto.BlockList
import com.thenewboston.data.dto.bankapi.blockdto.request.PostBlockRequest
import com.thenewboston.data.dto.bankapi.clean.request.PostCleanRequest
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.data.dto.common.request.UpdateTrustRequest
import com.thenewboston.data.dto.common.response.Bank
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
import com.thenewboston.data.dto.common.request.ConnectionRequest
import com.thenewboston.data.dto.bankapi.crawl.request.PostCrawlRequest
import com.thenewboston.data.dto.bankapi.crawl.response.Crawl
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlock
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlockList
import com.thenewboston.data.dto.bankapi.invalidblockdto.request.PostInvalidBlockRequest
import com.thenewboston.data.dto.bankapi.upgradenoticedto.UpgradeNoticeRequest
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.ValidatorConfirmationServicesList
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request.PostConfirmationServicesRequest
import com.thenewboston.data.dto.common.response.ValidatorList
import com.thenewboston.utils.PaginationOptions
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class BankRepository @Inject constructor(private val dataSource: BankDataSource) {

    suspend fun banks(offset: Int, limit: Int): Outcome<BankList> =
        dataSource.fetchBanks(PaginationOptions(offset, limit))

    suspend fun bankDetail(): Outcome<BankDetails> = dataSource.fetchBankDetails()

    suspend fun bankTransactions(offset: Int, limit: Int): Outcome<BankTransactionList> =
        dataSource.fetchBankTransactions(PaginationOptions(offset, limit))

    suspend fun validators(offset: Int, limit: Int): Outcome<ValidatorList> =
        dataSource.fetchValidators(PaginationOptions(offset, limit))

    suspend fun validator(nodeIdentifier: String) = dataSource.fetchValidator(nodeIdentifier)

    suspend fun accounts(offset: Int, limit: Int): Outcome<AccountList> =
        dataSource.fetchAccounts(PaginationOptions(offset, limit))

    suspend fun blocks(offset: Int, limit: Int): Outcome<BlockList> =
        dataSource.fetchBlocks(PaginationOptions(offset, limit))

    suspend fun updateBankTrust(request: UpdateTrustRequest): Outcome<Bank> = dataSource.updateBankTrust(request)

    suspend fun updateAccountTrust(accountNumber: String, request: UpdateTrustRequest): Outcome<Account> =
        dataSource.updateAccountTrust(accountNumber, request)

    suspend fun invalidBlocks(offset: Int, limit: Int): Outcome<InvalidBlockList> =
        dataSource.fetchInvalidBlocks(PaginationOptions(offset, limit))

    suspend fun sendInvalidBlock(request: PostInvalidBlockRequest): Outcome<InvalidBlock> =
        dataSource.sendInvalidBlock(request)

    suspend fun sendBlock(request: PostBlockRequest): Outcome<Block> = dataSource.sendBlock(request)

    suspend fun validatorConfirmationServices(offset: Int, limit: Int): Outcome<ValidatorConfirmationServicesList> =
        dataSource.fetchValidatorConfirmationServices(PaginationOptions(offset, limit))

    suspend fun sendValidatorConfirmationServices(request: PostConfirmationServicesRequest) =
        dataSource.sendValidatorConfirmationServices(request)

    suspend fun sendUpgradeNotice(request: UpgradeNoticeRequest) = dataSource.sendUpgradeNotice(request)

    suspend fun clean(): Outcome<Clean> = dataSource.fetchClean()

    suspend fun crawl(): Outcome<Crawl> = dataSource.fetchCrawl()

    suspend fun sendClean(request: PostCleanRequest): Outcome<Clean> = dataSource.sendClean(request)

    suspend fun sendConnectionRequests(request: ConnectionRequest) = dataSource.sendConnectionRequests(request)

    suspend fun sendCrawl(request: PostCrawlRequest): Outcome<Crawl> = dataSource.sendCrawl(request)
}
