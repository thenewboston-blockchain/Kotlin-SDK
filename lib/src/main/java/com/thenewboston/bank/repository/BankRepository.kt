package com.thenewboston.bank.repository

import com.thenewboston.bank.datasource.BankDataSource
import com.thenewboston.bank.model.BankList
import com.thenewboston.common.http.Outcome
import javax.inject.Inject

class BankRepository @Inject constructor(private val dataSource: BankDataSource) {

    suspend fun banks(): Outcome<BankList> = dataSource.fetchBanks()
}
