package com.thenewboston.bank.repository

import com.thenewboston.bank.datasource.BankDataSource
import com.thenewboston.bank.model.BankDetails
import com.thenewboston.bank.model.BankList
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import javax.inject.Inject

class BankRepository @Inject constructor(private val dataSource: BankDataSource) {

    suspend fun banks(): Outcome<BankList> = dataSource.fetchBanks()

    suspend fun bankDetail(
        bankConfig: BankConfig
    ): Outcome<BankDetails> = dataSource.fetchBankDetails(bankConfig)
}
