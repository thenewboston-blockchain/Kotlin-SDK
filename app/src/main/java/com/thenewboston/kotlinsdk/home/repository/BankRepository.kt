package com.thenewboston.kotlinsdk.home.repository

import com.thenewboston.kotlinsdk.LIST_DATA_LIMIT_DEFAULT
import com.thenewboston.kotlinsdk.LIST_DATA_OFFSET_DEFAULT
import com.thenewboston.kotlinsdk.network.apis.BankApis
import com.thenewboston.kotlinsdk.network.models.BankConfigModel
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BankRepository @Inject constructor(
    private val bank: BankApis
) {
    suspend fun getBankConfig(): Pair<String?, BankConfigModel?> {
        return NetworkUtils.callApiAndGetData<BankConfigModel> { bank.getConfig() }
    }

    suspend fun getNumOfConfServices(): Int? {
        val data = NetworkUtils.callApiAndGetData<GenericListDataModel> { bank.getValidatorConfirmationServices(
            LIST_DATA_LIMIT_DEFAULT, LIST_DATA_OFFSET_DEFAULT
        ) }
        return data.second?.count
    }

    suspend fun getAccountsForBanks(limit: Int, offset: Int): Pair<String?, GenericListDataModel?> {
        return NetworkUtils.callApiAndGetData<GenericListDataModel> { bank.getAccounts(limit, offset) }
    }
}
