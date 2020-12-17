package com.thenewboston.utils

import com.thenewboston.data.dto.bankapi.blockdto.request.PostBlockRequest
import com.thenewboston.data.dto.bankapi.common.request.UpdateTrustRequest
import com.thenewboston.data.dto.bankapi.invalidblockdto.request.PostInvalidBlockRequest
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.Accepted
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.http.headersOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BankApiMockEngine {

    fun getSuccess() = getBankMockEngine()
    fun getErrors() = getBankMockEngine(sendOnlyErrorResponses = true)

    fun postSuccess() = postBankEngine()
    fun postErrors() = postBankEngine(sendOnlyErrorResponses = true)
    fun postInvalidSuccess() = postBankEngine(sendInvalidResponses = true)

    fun patchSuccess() = patchBankEngine()
    fun patchEmptySuccess() = patchBankEngine(sendInvalidResponses = true)
    fun patchErrors() = patchBankEngine(true)

    private val json = listOf(ContentType.Application.Json.toString())
    private val responseHeaders = headersOf("Content-Type" to json)

    private fun getBankMockEngine(sendOnlyErrorResponses: Boolean = false) =
        HttpClient(MockEngine) {
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
                        BankAPIJsonMapper.INVALID_BLOCKS_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapInvalidBlocksToJson()
                            if (sendOnlyErrorResponses) {
                                respond(
                                    errorContent,
                                    InternalServerError,
                                    responseHeaders
                                )
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

            installJsonFeature()
        }

    private fun MockRequestHandleScope.sendResponse(
        content: String,
        errorContent: String,
        isError: Boolean
    ) = when {
        isError -> respond(errorContent, InternalServerError, responseHeaders)
        else -> respond(content, HttpStatusCode.OK, responseHeaders)
    }

    private fun postBankEngine(
        sendOnlyErrorResponses: Boolean = false,
        sendInvalidResponses: Boolean = false
    ) = HttpClient(MockEngine) {
        val errorContent = BankAPIJsonMapper.mapInternalServerErrorToJson()

        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    BankAPIJsonMapper.INVALID_BLOCKS_ENDPOINT -> {
                        val blockIdentifier = readBlockIdentifierFromRequest(request)
                        val content = BankAPIJsonMapper.mapInvalidBlockToJson(blockIdentifier)
                        val invalidContent = BankAPIJsonMapper.mapInvalidResponseForInvalidBlocksRequest()
                        when {
                            sendOnlyErrorResponses -> respond(errorContent, InternalServerError, responseHeaders)
                            sendInvalidResponses -> respond(invalidContent, Accepted, responseHeaders)
                            else -> respond(content, Accepted, responseHeaders)
                        }
                    }
                    BankAPIJsonMapper.BLOCKS_ENDPOINT -> {
                        val balanceKey = readBalanceKeyFromRequest(request)
                        val content = BankAPIJsonMapper.mapBlockToJson(balanceKey)
                        val invalidContent = BankAPIJsonMapper.mapBlockResponseForBlockRequest()
                        when {
                            sendOnlyErrorResponses -> respond(errorContent, InternalServerError, responseHeaders)
                            sendInvalidResponses -> respond(invalidContent, Accepted, responseHeaders)
                            else -> respond(content, Accepted, responseHeaders)
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

    private fun readBlockIdentifierFromRequest(request: HttpRequestData): String =
        request.extract<PostInvalidBlockRequest, String> { it.message.blockIdentifier }

    private fun readBalanceKeyFromRequest(request: HttpRequestData): String =
        request.extract<PostBlockRequest, String> { it.message.balanceKey }

    private inline fun <reified T, R> HttpRequestData.extract(extractor: (T) -> R): R {
        val requestBodyString = (this.body as TextContent).text
        return extractor(Json.decodeFromString<T>(requestBodyString))
    }

    private fun patchBankEngine(
        enableErrorResponse: Boolean = false,
        sendInvalidResponses: Boolean = false
    ) = HttpClient(MockEngine) {
        val errorContent = BankAPIJsonMapper.mapInternalServerErrorToJson()

        engine {
            addHandler { request ->
                when {
                    request.url.encodedPath == BankAPIJsonMapper.BANKS_TRUST_ENDPOINT -> {
                        val requestedTrust = readTrustFromRequest(request)
                        val content = BankAPIJsonMapper.mapBankTrustResponseToJson(requestedTrust)
                        val invalidContent = BankAPIJsonMapper.mapInvalidBankTrustResponseToJson()
                        when {
                            enableErrorResponse -> respond(
                                errorContent,
                                InternalServerError,
                                responseHeaders
                            )
                            sendInvalidResponses -> respond(
                                invalidContent,
                                Accepted,
                                responseHeaders
                            )
                            else -> respond(content, Accepted, responseHeaders)
                        }
                    }
                    request.url.encodedPath.startsWith(BankAPIJsonMapper.ACCOUNTS_ENDPOINT) -> {
                        val requestedTrust = readTrustFromRequest(request)
                        val responseBody = BankAPIJsonMapper.mapAccountToJson(trust = requestedTrust)
                        when {
                            enableErrorResponse -> respond(
                                errorContent,
                                InternalServerError,
                                responseHeaders
                            )
                            sendInvalidResponses -> respond(
                                BankAPIJsonMapper.mapEmptyAccountToJson(),
                                Accepted,
                                responseHeaders
                            )
                            else -> respond(responseBody, Accepted, responseHeaders)
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

    private fun readTrustFromRequest(request: HttpRequestData): Double =
        request.extract<UpdateTrustRequest, Double> { it.message.trust }

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
