package com.thenewboston.di.builder

import com.thenewboston.api.bankapi.repository.BankRepository
import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.config.Config
import com.thenewboston.data.dto.bankapi.common.request.PatchRequest
import com.thenewboston.data.dto.bankapi.common.request.TrustMessage
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
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.serializer
import javax.inject.Scope

@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class TNBScope

@Module
class TNBBankNetworkModule(private val config: Config) {

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
        serializersModule = SerializersModule {
            // https://github.com/ktorio/ktor/issues/1783
            contextual(serializer<PatchRequest<TrustMessage>>())
        }
    }

    @TNBScope
    @Provides
    fun provideBankHttpClient(
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
@Component(modules = [TNBBankNetworkModule::class])
interface TNBComponent {
    fun repository(): BankRepository
}
