package com.thenewboston.kotlinsdk.home.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thenewboston.kotlinsdk.LIST_DATA_LIMIT_DEFAULT
import com.thenewboston.kotlinsdk.home.repository.bank.BankRepo
import com.thenewboston.kotlinsdk.network.models.BankConfigModel
import com.thenewboston.kotlinsdk.network.models.GenericListDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class BankViewModel(
    private val repo: BankRepo
) : ViewModel() {
    /* Overview */
    private val mLiveDataBankOverview = MutableLiveData<Pair<String?, BankConfigModel?>>()
    val liveDataBankOverview : LiveData<Pair<String?, BankConfigModel?>> = mLiveDataBankOverview

    fun getBankConfig() {
        CoroutineScope(IO).launch {
            val data = repo.getBankConfig()
            mLiveDataBankOverview.postValue(data)
            getNumOfConfServices()
        }
    }

    private suspend fun getNumOfConfServices() {
        val bankData = mLiveDataBankOverview.value
        bankData?.second?.nConfServices = repo.getNumOfConfServices()
        mLiveDataBankOverview.postValue(bankData)
    }

    /* Accounts */
    private val mLiveDataBankAccounts = MutableLiveData<Pair<String?, GenericListDataModel?>>()
    val liveDataBankAccounts : LiveData<Pair<String?, GenericListDataModel?>> = mLiveDataBankAccounts
    fun getBankAccounts(limit: Int = LIST_DATA_LIMIT_DEFAULT, offset: Int) {
        CoroutineScope(IO).launch {
            val data = repo.getAccountsForBanks(limit, offset)
            mLiveDataBankAccounts.postValue(data)
        }
    }
}
