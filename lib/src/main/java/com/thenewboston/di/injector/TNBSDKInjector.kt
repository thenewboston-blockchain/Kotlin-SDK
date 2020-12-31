package com.thenewboston.di.injector

import com.thenewboston.api.bankapi.repository.BankRepository
import com.thenewboston.common.http.config.Config
import com.thenewboston.di.builder.DaggerTNBComponent
import com.thenewboston.di.builder.TNBBankNetworkModule
import com.thenewboston.di.builder.TNBComponent

class TNBSDKInjector(private val config: Config) {

    fun bankRepository(): BankRepository {
        return component().repository()
    }

    private fun component(): TNBComponent {
        return DaggerTNBComponent.builder()
            .tNBBankNetworkModule(TNBBankNetworkModule(config))
            .build()
    }
}
