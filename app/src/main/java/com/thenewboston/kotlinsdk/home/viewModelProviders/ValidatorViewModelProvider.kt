package com.thenewboston.kotlinsdk.home.viewModelProviders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thenewboston.kotlinsdk.home.repository.validator.ValidatorRepo
import com.thenewboston.kotlinsdk.home.viewmodels.ValidatorViewModel

@Suppress("UNCHECKED_CAST")
class ValidatorViewModelProvider(
    private val repo: ValidatorRepo
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ValidatorViewModel(repo) as T
    }
}
