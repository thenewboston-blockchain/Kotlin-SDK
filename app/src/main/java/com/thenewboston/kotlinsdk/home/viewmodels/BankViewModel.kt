package com.thenewboston.kotlinsdk.home.viewmodels


import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenewboston.kotlinsdk.LIST_DATA_LIMIT_DEFAULT
import com.thenewboston.kotlinsdk.home.repository.BankRepository
import com.thenewboston.kotlinsdk.network.models.BankConfigModel
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import kotlinx.coroutines.launch

class BankViewModel @ViewModelInject constructor(
    private val repo: BankRepository
) : ViewModel() {
    /* Overview */
    private val mLiveDataBankOverview = MutableLiveData<Pair<String?, BankConfigModel?>?>()
    val liveDataBankOverview : LiveData<Pair<String?, BankConfigModel?>?> = mLiveDataBankOverview

    private var tempData : Pair<String?, BankConfigModel?>? = null

    fun getBankConfig() {
        viewModelScope.launch {
            val data = repo.getBankConfig()
            Log.d("DATA_BANK", "In vm top -> $data")
            tempData = data
            if(tempData!=null) {
                getNumOfConfServices()
            } else {
                mLiveDataBankOverview.postValue(null)
            }
        }
    }

    private suspend fun getNumOfConfServices() {
        val nServices = repo.getNumOfConfServices()
        if(nServices!=null && tempData!=null)
            tempData?.second?.nConfServices = nServices
        Log.d("DATA_BANK", "In vm bottom -> $tempData")
        mLiveDataBankOverview.postValue(tempData)
    }

    /* Accounts */
    private val mLiveDataBankAccounts = MutableLiveData<Pair<String?, GenericListDataModel?>>()
    val liveDataBankAccounts : LiveData<Pair<String?, GenericListDataModel?>> = mLiveDataBankAccounts
    fun getBankAccounts(limit: Int = LIST_DATA_LIMIT_DEFAULT, offset: Int) {
        viewModelScope.launch {
            val data = repo.getAccountsForBanks(limit, offset)
            mLiveDataBankAccounts.postValue(data)
        }
    }
}
