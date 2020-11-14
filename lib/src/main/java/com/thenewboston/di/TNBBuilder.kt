package com.thenewboston.di

import com.thenewboston.bank.repository.BankRepository
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.config.BankConfig
import dagger.Component
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
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

