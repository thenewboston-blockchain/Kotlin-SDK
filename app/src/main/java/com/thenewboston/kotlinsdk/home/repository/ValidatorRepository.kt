package com.thenewboston.kotlinsdk.home.repository

import com.thenewboston.kotlinsdk.network.apis.ValidatorsApi
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import com.thenewboston.kotlinsdk.network.models.ValidatorConfigModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidatorRepository @Inject constructor(
    private val validators: ValidatorsApi
) {

    suspend fun getValidatorConfig(): Pair<String?, ValidatorConfigModel?> {
        return NetworkUtils.callApiAndGetData<ValidatorConfigModel> { validators.getConfig() }
    }

    suspend fun getValidatorAccounts(limit: Int, offset: Int): Pair<String?, GenericListDataModel?> {
        return NetworkUtils.callApiAndGetData<GenericListDataModel> { validators.getAccounts(limit, offset) }
    }

}
