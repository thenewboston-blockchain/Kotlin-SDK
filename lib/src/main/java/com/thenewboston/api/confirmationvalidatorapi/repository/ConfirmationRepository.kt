package com.thenewboston.api.confirmationvalidatorapi.repository

import com.thenewboston.api.confirmationvalidatorapi.datasource.ConfirmationDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.clean.request.PostCleanRequest
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.data.dto.bankapi.crawl.request.PostCrawlRequest
import com.thenewboston.data.dto.bankapi.crawl.response.Crawl
import com.thenewboston.data.dto.common.response.AccountListValidator
import com.thenewboston.data.dto.common.response.ValidatorDetails
import com.thenewboston.data.dto.common.response.ValidatorList
import com.thenewboston.data.dto.primaryvalidatorapi.bankdto.BankFromValidator
import com.thenewboston.data.dto.primaryvalidatorapi.bankdto.BankFromValidatorList
import com.thenewboston.utils.PaginationOptions
import javax.inject.Inject

class ConfirmationRepository @Inject constructor(private val dataSource: ConfirmationDataSource) {

    suspend fun accounts(offset: Int, limit: Int): Outcome<AccountListValidator> =
        dataSource.fetchAccounts(PaginationOptions(offset, limit))

    suspend fun validatorDetails(): Outcome<ValidatorDetails> =
        dataSource.fetchValidatorDetails()

    suspend fun bankFromValidator(nodeIdentifier: String): Outcome<BankFromValidator> =
        dataSource.fetchBankFromValidator(nodeIdentifier)

    suspend fun banksFromValidator(offset: Int, limit: Int): Outcome<BankFromValidatorList> =
        dataSource.fetchBanksFromValidator(PaginationOptions(offset, limit))

    suspend fun validators(offset: Int, limit: Int): Outcome<ValidatorList> =
        dataSource.fetchValidators(PaginationOptions(offset, limit))

    suspend fun validator(nodeIdentifier: String) = dataSource.fetchValidator(nodeIdentifier)

    suspend fun clean(): Outcome<Clean> = dataSource.fetchClean()

    suspend fun sendClean(request: PostCleanRequest): Outcome<Clean> = dataSource.sendClean(request)

    suspend fun validConfirmationBlocks(blockIdentifier: String) =
        dataSource.fetchValidConfirmationBlocks(blockIdentifier)

    suspend fun queuedConfirmationBlocks(blockIdentifier: String) =
        dataSource.fetchQueuedConfirmationBlocks(blockIdentifier)

    suspend fun crawl(): Outcome<Crawl> = dataSource.fetchCrawl()

    suspend fun sendCrawl(request: PostCrawlRequest): Outcome<Crawl> = dataSource.sendCrawl(request)
}
