package com.thenewboston.api.bankapi.repository

import com.thenewboston.api.bankapi.datasource.BankDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import com.thenewboston.data.dto.bankapi.accountdto.AccountListDTO
import com.thenewboston.data.dto.bankapi.bankdto.BankList
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorList
import javax.inject.Inject

class BankRepository @Inject constructor(private val dataSource: BankDataSource) {

    suspend fun banks(): Outcome<BankList> = dataSource.fetchBanks()

    suspend fun bankDetail(bankConfig: BankConfig): Outcome<BankDetails> = dataSource.fetchBankDetails(bankConfig)

    suspend fun bankTransactions(): Outcome<BankTransactionList> = dataSource.fetchBankTransactions()

    suspend fun validators(): Outcome<ValidatorList> = dataSource.fetchValidators()

    suspend fun validator(nodeIdentifier: String) = dataSource.fetchValidator(nodeIdentifier)

    suspend fun accounts(): Outcome<AccountListDTO> = dataSource.fetchAccounts()
}
