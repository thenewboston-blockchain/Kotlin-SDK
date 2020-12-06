package com.thenewboston.utils

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*

class BankApiMockEngine {



    fun getSuccess() = getBankMockEngine()

    fun getErrors() = getBankMockEngine(true)

    private val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))

    private fun getBankMockEngine(enableErrorResponse: Boolean = false) = HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    BankAPIJsonMapper.ACCOUNTS_ENDPOINT -> {
                        if (enableErrorResponse) {
                            respond(BankAPIJsonMapper.mapAccountsToJson(), HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(BankAPIJsonMapper.mapAccountsToJson(), HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.BANKS_ENDPOINT -> {
                        if (enableErrorResponse) {
                            respond(BankAPIJsonMapper.mapBanksToJson(), HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(BankAPIJsonMapper.mapBanksToJson(), HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.BANK_TRANSACTIONS_ENDPOINT -> {
                        if (enableErrorResponse) {
                            respond(BankAPIJsonMapper.mapBankTransactionsToJson(), HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(BankAPIJsonMapper.mapBankTransactionsToJson(), HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.BLOCKS_ENDPOINT -> {
                        if (enableErrorResponse) {
                            respond(BankAPIJsonMapper.mapBlocksToJson(), HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(BankAPIJsonMapper.mapBlocksToJson(), HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.VALIDATORS_ENDPOINT -> {
                        if (enableErrorResponse) {
                            respond(BankAPIJsonMapper.mapValidatorsToJson(), HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(BankAPIJsonMapper.mapValidatorsToJson(), HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.SINGLE_VALIDATOR_ENDPOINT -> {
                        if (enableErrorResponse) {
                            respond(BankAPIJsonMapper.mapValidatorToJson(), HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(BankAPIJsonMapper.mapValidatorToJson(), HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.CONFIG_ENDPOINT -> {
                        if (enableErrorResponse) {
                            respond(BankAPIJsonMapper.mapBankDetailToJson(), HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(BankAPIJsonMapper.mapBankDetailToJson(), HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }
                }
            }
        }
        val json = kotlinx.serialization.json.Json {
            isLenient = true
            ignoreUnknownKeys = true
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

}
