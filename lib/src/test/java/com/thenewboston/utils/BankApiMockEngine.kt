package com.thenewboston.utils

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*

class BankApiMockEngine {

    fun getSuccess() = getBankMockEngine()

    fun getErrors() = getBankMockEngine(true)

    private val json = listOf(ContentType.Application.Json.toString())
    private val responseHeaders = headersOf("Content-Type" to json)

    private fun getBankMockEngine(enableErrorResponse: Boolean = false) = HttpClient(MockEngine) {
        val errorContent = BankAPIJsonMapper.mapInternalServerErrorToJson()
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    BankAPIJsonMapper.ACCOUNTS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapAccountsToJson()
                        if (enableErrorResponse) {
                            respond(errorContent, HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(content, HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.BANKS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapBanksToJson()
                        if (enableErrorResponse) {
                            respond(errorContent, HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(content, HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.BANK_TRANSACTIONS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapBankTransactionsToJson()
                        if (enableErrorResponse) {
                            respond(errorContent, HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(content, HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.BLOCKS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapBlocksToJson()
                        if (enableErrorResponse) {
                            respond(errorContent, HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(content, HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.VALIDATORS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapValidatorsToJson()
                        if (enableErrorResponse) {
                            respond(errorContent, HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(content, HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.SINGLE_VALIDATOR_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapValidatorToJson()
                        if (enableErrorResponse) {
                            respond(errorContent, HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(content, HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.CONFIG_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapBankDetailToJson()
                        if (enableErrorResponse) {
                            respond(errorContent, HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(content, HttpStatusCode.OK, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.INVALID_BLOCKS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapInvalidBlocksToJson()
                        if (enableErrorResponse) {
                            respond(errorContent, HttpStatusCode.InternalServerError, responseHeaders)
                        } else {
                            respond(content, HttpStatusCode.OK, responseHeaders)
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
