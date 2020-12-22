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
import com.thenewboston.data.dto.bankapi.common.request.UpdateTrustRequest
import com.thenewboston.data.dto.bankapi.common.response.Bank
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlock
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlockList
import com.thenewboston.data.dto.bankapi.invalidblockdto.request.PostInvalidBlockRequest
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.ConfirmationServicesList
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request.PostConfirmationServicesRequest
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorList
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class BankRepository @Inject constructor(private val dataSource: BankDataSource) {

    suspend fun banks(): Outcome<BankList> = dataSource.fetchBanks()

    suspend fun bankDetail(): Outcome<BankDetails> = dataSource.fetchBankDetails()

    suspend fun bankTransactions(): Outcome<BankTransactionList> = dataSource.fetchBankTransactions()

    suspend fun validators(): Outcome<ValidatorList> = dataSource.fetchValidators()

    suspend fun validator(nodeIdentifier: String) = dataSource.fetchValidator(nodeIdentifier)

    suspend fun accounts(): Outcome<AccountList> = dataSource.fetchAccounts()

    suspend fun blocks(): Outcome<BlockList> = dataSource.fetchBlocks()

    suspend fun updateBankTrust(request: UpdateTrustRequest): Outcome<Bank> = dataSource.updateBankTrust(request)

    suspend fun updateAccountTrust(accountNumber: String, request: UpdateTrustRequest): Outcome<Account> =
        dataSource.updateAccountTrust(accountNumber, request)

    suspend fun invalidBlocks(): Outcome<InvalidBlockList> = dataSource.fetchInvalidBlocks()

    suspend fun sendInvalidBlock(request: PostInvalidBlockRequest): Outcome<InvalidBlock> =
        dataSource.sendInvalidBlock(request)

    suspend fun sendBlock(request: PostBlockRequest): Outcome<Block> = dataSource.sendBlock(request)

    suspend fun validatorConfirmationServices(): Outcome<ConfirmationServicesList> =
        dataSource.fetchValidatorConfirmationServices()

    suspend fun sendValidatorConfirmationServices(servicesRequest: PostConfirmationServicesRequest) =
        dataSource.sendValidatorConfirmationServices(servicesRequest)
}
