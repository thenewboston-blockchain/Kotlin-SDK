package com.thenewboston.di.injector

import com.thenewboston.api.bankapi.repository.BankRepository

class TNBSDKBuilder {

    fun initBankRepository(): BankRepository {
        val injector = TNBSDKInjector()
        return injector.bankRepository()
    }

    class Builder {

        fun buildBanksRepository(): BankRepository {
            val builder = TNBSDKBuilder()
            return builder.initBankRepository()
        }
    }
}

fun bankRepository(builder: TNBSDKBuilder.Builder.() -> Unit): BankRepository {
    return TNBSDKBuilder.Builder().apply(builder).buildBanksRepository()
}
