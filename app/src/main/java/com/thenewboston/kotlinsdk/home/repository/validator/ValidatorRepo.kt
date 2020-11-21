package com.thenewboston.kotlinsdk.home.repository.validator

import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import com.thenewboston.kotlinsdk.network.models.ValidatorConfigModel

interface ValidatorRepo {
    suspend fun getValidatorConfig(): Pair<String?, ValidatorConfigModel?>
    suspend fun getValidatorAccounts(limit: Int, offset: Int): Pair<String?, GenericListDataModel?>
}
