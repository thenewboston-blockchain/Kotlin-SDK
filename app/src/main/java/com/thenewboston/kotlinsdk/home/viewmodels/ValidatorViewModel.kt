package com.thenewboston.kotlinsdk.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thenewboston.kotlinsdk.LIST_DATA_LIMIT_DEFAULT
import com.thenewboston.kotlinsdk.home.repository.validator.ValidatorRepo
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ValidatorViewModel(
    private val repo: ValidatorRepo
) : ViewModel() {
    suspend fun getValidatorConfig() = repo.getValidatorConfig()
    /* Accounts */
    private val mValidatorAccountLiveData = MutableLiveData<Pair<String?, GenericListDataModel?>>()
    val validatorAccountLiveData: LiveData<Pair<String?, GenericListDataModel?>> = mValidatorAccountLiveData
    fun getValidatorAccounts(limit: Int = LIST_DATA_LIMIT_DEFAULT, offset: Int) {
        CoroutineScope(IO).launch {
            val data = repo.getValidatorAccounts(limit, offset)
            mValidatorAccountLiveData.postValue(data)
        }
    }
}
