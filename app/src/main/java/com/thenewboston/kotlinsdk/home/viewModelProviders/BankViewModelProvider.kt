package com.thenewboston.kotlinsdk.home.viewModelProviders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thenewboston.kotlinsdk.home.repository.bank.BankRepo
import com.thenewboston.kotlinsdk.home.viewmodels.BankViewModel

@Suppress("UNCHECKED_CAST")
class BankViewModelProvider(
    private val repo: BankRepo
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BankViewModel(repo) as T
    }
}
