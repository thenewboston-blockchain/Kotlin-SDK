package com.thenewboston.di.injector

import com.thenewboston.api.bankapi.repository.BankRepository
import com.thenewboston.common.http.config.Config

class TNBSDKBuilder(private val config: Config) {

    fun initBankRepository(): BankRepository {
        val injector = TNBSDKInjector(config)
        return injector.bankRepository()
    }

    class Builder {

        private lateinit var config: Config

        fun withConfig(config: Config) = apply { this.config = config }

        fun buildBanksRepository(): BankRepository {
            val builder = TNBSDKBuilder(config)
            return builder.initBankRepository()
        }
    }
}

fun bankRepository(builder: TNBSDKBuilder.Builder.() -> Unit): BankRepository {
    return TNBSDKBuilder.Builder().apply(builder).buildBanksRepository()
}
