package com.thenewboston.kotlinsdk.splash

import androidx.lifecycle.ViewModel
import com.thenewboston.kotlinsdk.home.repository.bank.BankRepo
import com.thenewboston.kotlinsdk.splash.repo.SplashRepo

class SplashViewModel(
    private val repo: SplashRepo
) : ViewModel() {
    suspend fun getPrimaryValidatorId() = repo.getPrimaryValidatorIPAddress()
}
