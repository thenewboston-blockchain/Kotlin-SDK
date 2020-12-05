package com.thenewboston.kotlinsdk.splash

import com.thenewboston.kotlinsdk.network.apis.BankApis
import com.thenewboston.kotlinsdk.network.models.BankConfigModel
import com.thenewboston.kotlinsdk.utils.NetworkUtils
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val bank: BankApis
) {
    suspend fun getPrimaryValidatorIPAddress(): String? {
        val data = NetworkUtils.callApiAndGetData<BankConfigModel> { bank.getConfig() }
        return data.second?.primaryValidator?.ipAddress
    }
}
