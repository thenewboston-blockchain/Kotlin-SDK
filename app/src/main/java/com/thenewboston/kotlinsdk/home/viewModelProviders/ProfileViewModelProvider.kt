package com.thenewboston.kotlinsdk.home.viewModelProviders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thenewboston.kotlinsdk.home.repository.profile.ProfileRepo
import com.thenewboston.kotlinsdk.home.viewmodels.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ProfileViewModelProvider(
    private val repo: ProfileRepo
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repo) as T
    }
}
