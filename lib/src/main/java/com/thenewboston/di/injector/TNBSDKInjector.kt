package com.thenewboston.di.injector

import com.thenewboston.api.bankapi.repository.BankRepository
import com.thenewboston.api.confirmationvalidatorapi.repository.ConfirmationRepository
import com.thenewboston.api.primaryvalidatorapi.repository.PrimaryRepository
import com.thenewboston.common.http.config.Config
import com.thenewboston.di.builder.DaggerTNBComponent
import com.thenewboston.di.builder.TNBComponent
import com.thenewboston.di.builder.TNBNetworkModule

class TNBSDKInjector(private val config: Config) {

    fun bankRepository(): BankRepository {
        return component().bankRepository()
    }

    fun primaryRepository(): PrimaryRepository {
        return component().primaryValidatorRepository()
    }

    fun confirmationValidatorRepository(): ConfirmationRepository {
        return component().confirmationValidatorRepository()
    }

    private fun component(): TNBComponent {
        return DaggerTNBComponent.builder()
            .tNBNetworkModule(TNBNetworkModule(config))
            .build()
    }
}
