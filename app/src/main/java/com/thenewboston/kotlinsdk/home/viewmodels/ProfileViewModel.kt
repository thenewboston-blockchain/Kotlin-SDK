package com.thenewboston.kotlinsdk.home.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thenewboston.kotlinsdk.ACCOUNT_NO
import com.thenewboston.kotlinsdk.LIST_DATA_LIMIT_DEFAULT
import com.thenewboston.kotlinsdk.home.repository.profile.ProfileRepo
import com.thenewboston.kotlinsdk.utils.TinyDB

class ProfileViewModel(
    private val repo: ProfileRepo
) : ViewModel() {
    val accountNumber = MutableLiveData<String>()
    fun updateAccountNumber(context: Context, accNo: String) {
        TinyDB.saveDataLocally(context, ACCOUNT_NO, accNo)
        accountNumber.postValue(accNo)
    }
    suspend fun getAccountBalance(accountNumber: String) = repo.getAccountBalance(accountNumber)
    suspend fun getAccountTransactions(accountNumber: String, limit: Int = LIST_DATA_LIMIT_DEFAULT, offset: Int) = repo.getAccountTransactions(accountNumber, limit, offset)
}
