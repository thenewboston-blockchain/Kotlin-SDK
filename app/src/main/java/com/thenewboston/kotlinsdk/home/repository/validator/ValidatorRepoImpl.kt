package com.thenewboston.kotlinsdk.home.repository.validator

import com.thenewboston.kotlinsdk.network.apis.ValidatorsApi
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import com.thenewboston.kotlinsdk.network.models.ValidatorConfigModel
import com.thenewboston.kotlinsdk.utils.NetworkUtils

class ValidatorRepoImpl(
    private val validators: ValidatorsApi
) : ValidatorRepo {

    override suspend fun getValidatorConfig(): Pair<String?, ValidatorConfigModel?> {
        return NetworkUtils.callApiAndGetData<ValidatorConfigModel> { validators.getConfig() }
    }

    override suspend fun getValidatorAccounts(limit: Int, offset: Int): Pair<String?, GenericListDataModel?> {
        return NetworkUtils.callApiAndGetData<GenericListDataModel> { validators.getAccounts(limit, offset) }
    }

}
