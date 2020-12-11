package com.thenewboston.utils

import com.thenewboston.data.dto.bankapi.common.request.UpdateTrustRequest
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import io.ktor.http.content.TextContent
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class BankApiMockEngine {

    fun getSuccess() = getBankMockEngine()

    fun getErrors() = getBankMockEngine(sendOnlyErrorResponses = true)

    fun patchSuccess() = patchBankEngine()

    fun patchEmptySuccess() = patchBankEngine(isInvalidResponse = true)

    fun patchErrors() = patchBankEngine(true)

    private val json = listOf(ContentType.Application.Json.toString())
    private val responseHeaders = headersOf("Content-Type" to json)

    private fun getBankMockEngine(sendOnlyErrorResponses: Boolean = false) = HttpClient(MockEngine) {
        val errorContent = BankAPIJsonMapper.mapInternalServerErrorToJson()
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    BankAPIJsonMapper.ACCOUNTS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapAccountsToJson()
                        sendResponse(content, errorContent, sendOnlyErrorResponses)
                    }
                    BankAPIJsonMapper.BANKS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapBanksToJson()
                        sendResponse(content, errorContent, sendOnlyErrorResponses)
                    }
                    BankAPIJsonMapper.BANK_TRANSACTIONS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapBankTransactionsToJson()
                        sendResponse(content, errorContent, sendOnlyErrorResponses)
                    }
                    BankAPIJsonMapper.BLOCKS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapBlocksToJson()
                        sendResponse(content, errorContent, sendOnlyErrorResponses)
                    }
                    BankAPIJsonMapper.VALIDATORS_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapValidatorsToJson()
                        sendResponse(content, errorContent, sendOnlyErrorResponses)
                    }
                    BankAPIJsonMapper.SINGLE_VALIDATOR_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapValidatorToJson()
                        sendResponse(content, errorContent, sendOnlyErrorResponses)
                    }
                    BankAPIJsonMapper.CONFIG_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapBankDetailToJson()
                        sendResponse(content, errorContent, sendOnlyErrorResponses)
                    }
                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }
                }
            }
        }

        installJsonFeature()
    }

    private fun MockRequestHandleScope.sendResponse(
        content: String,
        errorContent: String,
        isError: Boolean
    ) = when {
        isError -> respond(errorContent, HttpStatusCode.InternalServerError, responseHeaders)
        else -> respond(content, HttpStatusCode.OK, responseHeaders)
    }

    private fun patchBankEngine(
        enableErrorResponse: Boolean = false,
        isInvalidResponse: Boolean = false
    ) = HttpClient(MockEngine) {
        val errorContent = BankAPIJsonMapper.mapInternalServerErrorToJson()

        engine {
            addHandler { request ->
                when {
                    request.url.encodedPath == BankAPIJsonMapper.BANKS_TRUST_ENDPOINT -> {
                        val content = BankAPIJsonMapper.mapBankTrustResponseToJson()
                        val invalidContent = BankAPIJsonMapper.mapInvalidBankTrustResponseToJson()
                        when {
                            enableErrorResponse -> respond(
                                errorContent,
                                HttpStatusCode.InternalServerError,
                                responseHeaders
                            )
                            isInvalidResponse -> respond(
                                invalidContent,
                                HttpStatusCode.Accepted,
                                responseHeaders
                            )
                            else -> respond(content, HttpStatusCode.Accepted, responseHeaders)
                        }
                    }
                    request.url.encodedPath.startsWith(BankAPIJsonMapper.ACCOUNTS_ENDPOINT) -> {
                        val requestBodyString = (request.body as TextContent).text
                        val requestedTrust =
                            Json.decodeFromString<UpdateTrustRequest>(requestBodyString).message.trust
                        val responseBody = Json.encodeToString(Mocks.account(trust = requestedTrust))
                        when {
                            enableErrorResponse -> respond(
                                errorContent,
                                HttpStatusCode.InternalServerError,
                                responseHeaders
                            )
                            else -> respond(responseBody, HttpStatusCode.Accepted, responseHeaders)
                        }
                    }
                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }
                }
            }
        }

        installJsonFeature()

        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }

    private fun HttpClientConfig<MockEngineConfig>.installJsonFeature() {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json())
        }
    }

    private fun json(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
}
