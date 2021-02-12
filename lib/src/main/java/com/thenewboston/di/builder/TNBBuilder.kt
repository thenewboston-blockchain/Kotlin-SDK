package com.thenewboston.di.builder

import com.thenewboston.api.bankapi.repository.BankRepository
import com.thenewboston.api.confirmationvalidatorapi.repository.ConfirmationRepository
import com.thenewboston.api.primaryvalidatorapi.repository.PrimaryRepository
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.config.Config
import dagger.Component
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Scope
import kotlinx.serialization.json.Json

@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class TNBScope

@Module
class TNBNetworkModule(private val config: Config) {

    @TNBScope
    @Provides
    fun provideEngine(): HttpClientEngine {
        return CIO.create()
    }

    @TNBScope
    @Provides
    fun provideNetworkClient(client: HttpClient): NetworkClient = NetworkClient(client)

    @TNBScope
    @Provides
    fun provideJsonConfiguration(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    @TNBScope
    @Provides
    fun provideHttpClient(
        engine: HttpClientEngine,
        json: Json
    ): HttpClient =
        HttpClient(engine) {
            defaultRequest {
                this.host = config.ipAddress
                this.port = config.port
                contentType(ContentType.Application.Json)
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
        }
}

@TNBScope
@Component(modules = [TNBNetworkModule::class])
interface TNBComponent {
    fun bankRepository(): BankRepository
    fun primaryValidatorRepository(): PrimaryRepository
    fun confirmationValidatorRepository(): ConfirmationRepository
}
