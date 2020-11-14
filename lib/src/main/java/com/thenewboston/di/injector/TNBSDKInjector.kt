package com.thenewboston.di.injector

import com.thenewboston.bank.repository.BankRepository
import com.thenewboston.di.DaggerTNBComponent
import com.thenewboston.di.TNBComponent
import com.thenewboston.di.TNBNetworkModule

class TNBSDKInjector {

    fun bankRepository(): BankRepository {
        return component().repository()
    }

    private fun component(): TNBComponent {
        return DaggerTNBComponent.builder()
            .tNBNetworkModule(TNBNetworkModule())
            .build()
    }
}