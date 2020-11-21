package com.thenewboston.kotlinsdk.home.viewmodels

import androidx.lifecycle.ViewModel
import com.thenewboston.kotlinsdk.LIST_DATA_LIMIT_DEFAULT
import com.thenewboston.kotlinsdk.home.repository.validator.ValidatorRepo

class ValidatorViewModel(
    private val repo: ValidatorRepo
) : ViewModel() {
    suspend fun getValidatorConfig() = repo.getValidatorConfig()
    suspend fun getValidatorAccounts(limit: Int = LIST_DATA_LIMIT_DEFAULT, offset: Int) = repo.getValidatorAccounts(limit, offset)
}
