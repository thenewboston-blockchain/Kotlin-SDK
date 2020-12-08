package com.thenewboston.kotlinsdk.home.viewmodels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenewboston.kotlinsdk.ACCOUNT_NO
import com.thenewboston.kotlinsdk.LIST_DATA_LIMIT_DEFAULT
import com.thenewboston.kotlinsdk.home.repository.ProfileRepository
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import com.thenewboston.kotlinsdk.utils.TinyDB
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    private val repo: ProfileRepository
) : ViewModel() {
    /* Overview */
    private val mAccountBalanceLiveData = MutableLiveData<Int?>()
    val accountBalanceLiveData : LiveData<Int?> = mAccountBalanceLiveData

    val accountNumber = MutableLiveData<String?>()

    fun updateAccountNumber(context: Context, accNo: String) {
        TinyDB.saveDataLocally(context, ACCOUNT_NO, accNo)
        accountNumber.postValue(accNo)
        getAccountBalance(accNo)
    }

    fun getAccountBalance(accountNumber: String) {
        viewModelScope.launch {
            val balance = repo.getAccountBalance(accountNumber)
            mAccountBalanceLiveData.postValue(balance)
        }
    }

    /* Transactions */
    private val mLiveDataProfileTransactions = MutableLiveData<Pair<String?, GenericListDataModel?>>()
    val liveDataProfileTransactions : LiveData<Pair<String?, GenericListDataModel?>> = mLiveDataProfileTransactions
    fun getAccountTransactions(accountNumber: String, limit: Int = LIST_DATA_LIMIT_DEFAULT, offset: Int) {
        viewModelScope.launch {
            val data = repo.getAccountTransactions(accountNumber, limit, offset)
            mLiveDataProfileTransactions.postValue(data)
        }
    }
}
