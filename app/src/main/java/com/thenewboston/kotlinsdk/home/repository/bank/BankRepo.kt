package com.thenewboston.kotlinsdk.home.repository.bank

import com.thenewboston.kotlinsdk.network.models.BankConfigModel
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel

interface BankRepo {
    suspend fun getBankConfig(): Pair<String?, BankConfigModel?>
    suspend fun getNumOfConfServices(): Int?
    suspend fun getAccountsForBanks(limit: Int, offset: Int): Pair<String?, GenericListDataModel?>
}
