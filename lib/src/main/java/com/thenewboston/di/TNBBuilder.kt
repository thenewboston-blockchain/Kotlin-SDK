package com.thenewboston.di

import com.thenewboston.bank.repository.BankRepository
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.config.BankConfig
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class TNBScope

@Module
class TNBNetworkModule {

    @TNBScope
    @Provides
    fun provideBankConfig(): BankConfig = BankConfig()

    @TNBScope
    @Provides
    fun provideNetworkClient(bankConfig: BankConfig): NetworkClient = NetworkClient(bankConfig)
}

@TNBScope
@Component(modules = [TNBNetworkModule::class])
interface TNBComponent {
    fun repository(): BankRepository
}
