package com.thenewboston.kotlinsdk.home.repository


import com.thenewboston.kotlinsdk.network.apis.BankApis
import com.thenewboston.kotlinsdk.network.apis.ValidatorsApi
import com.thenewboston.kotlinsdk.network.models.ProfileBalanceObject
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val bank: BankApis,
    private val validators: ValidatorsApi
) {
    suspend fun getAccountBalance(accountNumber: String): Int? {
        val data = NetworkUtils.callApiAndGetData<ProfileBalanceObject> { validators.getAccountBalance(accountNumber) }
        return data.second?.balance
    }

    suspend fun getAccountTransactions(accountNumber: String, limit: Int, offset: Int): Pair<String?, GenericListDataModel?> {
        return NetworkUtils.callApiAndGetData<GenericListDataModel> {
            bank.getAccountTransactions(accountNumber, limit, offset)
        }
    }
}
