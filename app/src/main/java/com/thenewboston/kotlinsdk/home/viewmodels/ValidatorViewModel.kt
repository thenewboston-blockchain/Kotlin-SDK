package com.thenewboston.kotlinsdk.home.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenewboston.kotlinsdk.LIST_DATA_LIMIT_DEFAULT
import com.thenewboston.kotlinsdk.home.repository.ValidatorRepository
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import com.thenewboston.kotlinsdk.network.models.ValidatorConfigModel
import kotlinx.coroutines.launch

class ValidatorViewModel @ViewModelInject constructor(
    private val repo: ValidatorRepository
) : ViewModel() {
    /* Config */
    private val mValidatorConfigLiveData = MutableLiveData<Pair<String?, ValidatorConfigModel?>>()
    val validatorConfigLiveData: LiveData<Pair<String?, ValidatorConfigModel?>> = mValidatorConfigLiveData
    fun getValidatorConfig() {
        viewModelScope.launch {
            val data = repo.getValidatorConfig()
            mValidatorConfigLiveData.postValue(data)
        }
    }
    /* Accounts */
    private val mValidatorAccountLiveData = MutableLiveData<Pair<String?, GenericListDataModel?>>()
    val validatorAccountLiveData: LiveData<Pair<String?, GenericListDataModel?>> = mValidatorAccountLiveData
    fun getValidatorAccounts(limit: Int = LIST_DATA_LIMIT_DEFAULT, offset: Int) {
        viewModelScope.launch {
            val data = repo.getValidatorAccounts(limit, offset)
            mValidatorAccountLiveData.postValue(data)
        }
    }
}
