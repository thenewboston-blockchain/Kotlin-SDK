package com.thenewboston.di.injector

import com.thenewboston.api.bankapi.repository.BankRepository
import com.thenewboston.di.builder.DaggerTNBComponent
import com.thenewboston.di.builder.TNBBankNetworkModule
import com.thenewboston.di.builder.TNBComponent

class TNBSDKInjector {

    fun bankRepository(): BankRepository {
        return component().repository()
    }

    private fun component(): TNBComponent {
        return DaggerTNBComponent.builder()
            .tNBBankNetworkModule(TNBBankNetworkModule())
            .build()
    }
}
