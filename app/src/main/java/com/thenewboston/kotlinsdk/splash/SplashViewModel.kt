package com.thenewboston.kotlinsdk.splash

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenewboston.kotlinsdk.PRIMARY_VALIDATOR
import com.thenewboston.kotlinsdk.utils.TinyDB
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(
    private val repo: SplashRepository
) : ViewModel() {
    val savedPrimaryValidator = MutableLiveData<Boolean?>(null)
    fun getPrimaryValidatorId(context: Context) {
        viewModelScope.launch {
            val ip = repo.getPrimaryValidatorIPAddress()
            if(ip!=null) {
                TinyDB.saveDataLocally(context, PRIMARY_VALIDATOR, ip)
                savedPrimaryValidator.postValue(true)
            } else {
                savedPrimaryValidator.postValue(false)
            }

        }
    }
}
