package com.thenewboston.di.injector

import com.thenewboston.api.bankapi.repository.BankRepository
import com.thenewboston.api.confirmationvalidatorapi.repository.ConfirmationRepository
import com.thenewboston.api.primaryvalidatorapi.repository.PrimaryRepository
import com.thenewboston.common.http.config.Config

class TNBSDKBuilder(private val config: Config) {

    fun initBankRepository(): BankRepository {
        val injector = TNBSDKInjector(config)
        return injector.bankRepository()
    }

    fun initPrimaryValidatorRepository(): PrimaryRepository {
        val injector = TNBSDKInjector(config)
        return injector.primaryRepository()
    }

    fun initConfirmationValidatorRepository(): ConfirmationRepository {
        val injector = TNBSDKInjector(config)
        return injector.confirmationValidatorRepository()
    }

    class Builder {

        private lateinit var config: Config

        fun withConfig(config: Config) = apply { this.config = config }

        fun buildBanksRepository(): BankRepository {
            val builder = TNBSDKBuilder(config)
            return builder.initBankRepository()
        }

        fun buildPrimaryValidatorRepository(): PrimaryRepository {
            val builder = TNBSDKBuilder(config)
            return builder.initPrimaryValidatorRepository()
        }

        fun buildConfirmationValidatorRepository(): ConfirmationRepository {
            val builder = TNBSDKBuilder(config)
            return builder.initConfirmationValidatorRepository()
        }
    }
}

fun bankRepository(builder: TNBSDKBuilder.Builder.() -> Unit): BankRepository {
    return TNBSDKBuilder.Builder().apply(builder).buildBanksRepository()
}

fun primaryValidatorRepository(builder: TNBSDKBuilder.Builder.() -> Unit): PrimaryRepository {
    return TNBSDKBuilder.Builder().apply(builder).buildPrimaryValidatorRepository()
}

fun confirmationValidatorRepository(builder: TNBSDKBuilder.Builder.() -> Unit): ConfirmationRepository {
    return TNBSDKBuilder.Builder().apply(builder).buildConfirmationValidatorRepository()
}
