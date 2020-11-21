package com.thenewboston.kotlinsdk.splash.repo

import com.thenewboston.kotlinsdk.network.apis.BankApis
import com.thenewboston.kotlinsdk.network.models.BankConfigModel
import com.thenewboston.kotlinsdk.utils.NetworkUtils

class SplashRepoImpl(
    private val bank: BankApis
) : SplashRepo {
    override suspend fun getPrimaryValidatorIPAddress(): String? {
        val data = NetworkUtils.callApiAndGetData<BankConfigModel> { bank.getConfig() }
        return data.second?.primaryValidator?.ipAddress
    }
}
