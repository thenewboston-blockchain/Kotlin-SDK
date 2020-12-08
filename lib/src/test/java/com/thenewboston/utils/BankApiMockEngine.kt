package com.thenewboston.utils

import com.thenewboston.data.dto.bankapi.accountdto.PatchAccountRequestBody
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import io.ktor.http.content.TextContent
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BankApiMockEngine {

    fun getSuccess() = getBankMockEngine()

    fun getErrors() = getBankMockEngine(enableErrorResponse = true)

    private val json = listOf(ContentType.Application.Json.toString())
    private val responseHeaders = headersOf("Content-Type" to json)

    private fun getBankMockEngine(enableErrorResponse: Boolean = false) = HttpClient(MockEngine) {
        val errorContent = BankAPIJsonMapper.mapInternalServerErrorToJson()
        engine {
            addHandler { request ->
                when (request.method) {
                    HttpMethod.Get -> {
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
                            else -> {
                                error("Unhandled ${request.url.encodedPath}")
                            }
                        }
                    }
                    HttpMethod.Patch -> {
                        when {
                            request.url.encodedPath.startsWith(BankAPIJsonMapper.ACCOUNTS_ENDPOINT) -> {
                                val bodyStr = (request.body as TextContent).text
                                val newTrust = Json.decodeFromString<PatchAccountRequestBody>(bodyStr).message.trust
                                val responseBody = Json.encodeToString(Mocks.account(trust = newTrust))
                                if (enableErrorResponse) {
                                    respond(errorContent, HttpStatusCode.InternalServerError, responseHeaders)
                                } else {
                                    respond(responseBody, HttpStatusCode.OK, responseHeaders)
                                }
                            }
                            else -> {
                                error("Unhandled ${request.url.encodedPath}")
                            }
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
