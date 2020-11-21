package com.thenewboston.kotlinsdk.home.repository.profile


import com.thenewboston.kotlinsdk.network.apis.BankApis
import com.thenewboston.kotlinsdk.network.apis.ValidatorsApi
import com.thenewboston.kotlinsdk.network.models.BalanceObject
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import com.thenewboston.kotlinsdk.utils.NetworkUtils

class ProfileRepoImpl(
    private val bank: BankApis,
    private val validators: ValidatorsApi
) : ProfileRepo {
    override suspend fun getAccountBalance(accountNumber: String): Int? {
        val data = NetworkUtils.callApiAndGetData<BalanceObject> { validators.getAccountBalance(accountNumber) }
        return data.second?.balance
    }

    override suspend fun getAccountTransactions(accountNumber: String, limit: Int, offset: Int): Pair<String?, GenericListDataModel?> {
        return NetworkUtils.callApiAndGetData<GenericListDataModel> {
            bank.getAccountTransactions(accountNumber, limit, offset)
        }
    }
}
