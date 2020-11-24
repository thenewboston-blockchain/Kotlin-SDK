package com.thenewboston.kotlinsdk.home.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thenewboston.kotlinsdk.ACCOUNT_NO
import com.thenewboston.kotlinsdk.LIST_DATA_LIMIT_DEFAULT
import com.thenewboston.kotlinsdk.home.repository.profile.ProfileRepo
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import com.thenewboston.kotlinsdk.utils.TinyDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repo: ProfileRepo
) : ViewModel() {
    /* Overview */
    private val mAccountBalanceLiveData = MutableLiveData<Int>()
    val accountBalanceLiveData : LiveData<Int> = mAccountBalanceLiveData

    val accountNumber = MutableLiveData<String>()

    fun updateAccountNumber(context: Context, accNo: String) {
        TinyDB.saveDataLocally(context, ACCOUNT_NO, accNo)
        accountNumber.postValue(accNo)
        CoroutineScope(IO).launch {
            getAccountBalance(accNo)
        }
    }

    fun getAccountBalance(accountNumber: String) {
        CoroutineScope(IO).launch {
            val balance = repo.getAccountBalance(accountNumber)
            mAccountBalanceLiveData.postValue(balance)
        }
    }

    /* Transactions */
    private val mLiveDataProfileTransactions = MutableLiveData<Pair<String?, GenericListDataModel?>>()
    val liveDataProfileTransactions : LiveData<Pair<String?, GenericListDataModel?>> = mLiveDataProfileTransactions
    fun getAccountTransactions(accountNumber: String, limit: Int = LIST_DATA_LIMIT_DEFAULT, offset: Int) {
        CoroutineScope(IO).launch {
            val data = repo.getAccountTransactions(accountNumber, limit, offset)
            mLiveDataProfileTransactions.postValue(data)
        }
    }
}
