package com.thenewboston.kotlinsdk.home.viewmodels

import androidx.lifecycle.ViewModel
import com.thenewboston.kotlinsdk.LIST_DATA_LIMIT_DEFAULT
import com.thenewboston.kotlinsdk.home.repository.bank.BankRepo

class BankViewModel(
    private val repo: BankRepo
) : ViewModel() {
    suspend fun getBankConfig() = repo.getBankConfig()
    suspend fun getNumOfConfServices() = repo.getNumOfConfServices()
    suspend fun getBankAccounts(limit: Int = LIST_DATA_LIMIT_DEFAULT, offset: Int) = repo.getAccountsForBanks(limit, offset)
}
