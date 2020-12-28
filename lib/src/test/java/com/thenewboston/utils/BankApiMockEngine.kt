package com.thenewboston.utils

import com.thenewboston.data.dto.bankapi.blockdto.request.PostBlockRequest
import com.thenewboston.data.dto.bankapi.common.request.UpdateTrustRequest
import com.thenewboston.data.dto.bankapi.invalidblockdto.request.PostInvalidBlockRequest
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request.Message
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request.PostConfirmationServicesRequest
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.Accepted
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.content.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BankApiMockEngine {

    fun getSuccess() = getBankMockEngine()
    fun getErrors() = getBankMockEngine(sendOnlyErrorResponses = true)
    fun getEmptySuccess() = getBankMockEngine(sendInvalidResponses = true)

    fun postSuccess() = postBankEngine()
    fun postErrors() = postBankEngine(sendOnlyErrorResponses = true)
    fun postInvalidSuccess() = postBankEngine(sendInvalidResponses = true)

    fun patchSuccess() = patchBankEngine()
    fun patchEmptySuccess() = patchBankEngine(sendInvalidResponses = true)
    fun patchErrors() = patchBankEngine(true)

    private val json = listOf(ContentType.Application.Json.toString())
    private val responseHeaders = headersOf("Content-Type" to json)

    private fun getBankMockEngine(sendOnlyErrorResponses: Boolean = false, sendInvalidResponses: Boolean = false) =
        HttpClient(MockEngine) {
            val errorContent = BankAPIJsonMapper.mapInternalServerErrorToJson()
            engine {
                addHandler { request ->
                    when (request.url.encodedPath) {
                        BankAPIJsonMapper.ACCOUNTS_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapAccountsToJson()
                            val emptyContent = BankAPIJsonMapper.mapEmptyAccountsToJson()
                            sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                        }
                        BankAPIJsonMapper.BANKS_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapBanksToJson()
                            val emptyContent = BankAPIJsonMapper.mapEmptyBanksToJson()
                            sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                        }
                        BankAPIJsonMapper.BANK_TRANSACTIONS_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapBankTransactionsToJson()
                            val emptyContent = BankAPIJsonMapper.mapEmptyBankTransactionsToJson()
                            sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                        }
                        BankAPIJsonMapper.BLOCKS_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapBlocksToJson()
                            val emptyContent = BankAPIJsonMapper.mapEmptyBlocksToJson()
                            sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                        }
                        BankAPIJsonMapper.VALIDATORS_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapValidatorsToJson()
                            val emptyContent = BankAPIJsonMapper.mapEmptyValidatorsToJson()
                            sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                        }
                        BankAPIJsonMapper.SINGLE_VALIDATOR_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapValidatorToJson()
                            val emptyContent = BankAPIJsonMapper.mapEmptyValidatorToJson()
                            sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                        }
                        BankAPIJsonMapper.CONFIG_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapBankDetailToJson()
                            val emptyContent = BankAPIJsonMapper.mapEmptyBankDetailToJson()
                            sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                        }
                        BankAPIJsonMapper.INVALID_BLOCKS_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapInvalidBlocksToJson()
                            val emptyContent = BankAPIJsonMapper.mapEmptyInvalidBlocksToJson()
                            sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                        }
                        BankAPIJsonMapper.VALIDATOR_CONFIRMATION_SERVICES_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapValidatorConfirmationServicesToJson()
                            val emptyContent = BankAPIJsonMapper.mapEmptyValidatorConfirmationServicesToJson()
                            sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                        }
                        BankAPIJsonMapper.CLEAN_ENDPOINT -> {
                            val content = BankAPIJsonMapper.mapCleanToJson()
                            val emptyContent = BankAPIJsonMapper.mapEmptyCleanToJson()
                            sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
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
        emptyContent: String,
        isError: Boolean,
        isInvalidResponse: Boolean
    ) = when {
        isError -> respond(errorContent, InternalServerError, responseHeaders)
        isInvalidResponse -> respond(emptyContent, HttpStatusCode.OK, responseHeaders)
        else -> respond(content, HttpStatusCode.OK, responseHeaders)
    }

    private fun postBankEngine(
        sendOnlyErrorResponses: Boolean = false,
        sendInvalidResponses: Boolean = false
    ) = HttpClient(MockEngine) {
        val errorContent = BankAPIJsonMapper.mapInternalServerErrorToJson()

        engine {
            addHandler { request ->
                when {
                    request.url.encodedPath.startsWith(BankAPIJsonMapper.VALIDATOR_CONFIRMATION_SERVICES_ENDPOINT) -> {
                        val content = BankAPIJsonMapper.mapValidatorConfirmationServiceToJson(readMessageFromRequest(request))
                        val invalidContent = BankAPIJsonMapper.mapEmptyValidatorConfirmationServiceToJson()
                        sendResponse(content, errorContent, invalidContent, sendOnlyErrorResponses, sendInvalidResponses)
                    }
                    request.url.encodedPath.startsWith(BankAPIJsonMapper.UPGRADE_NOTICE_ENDPOINT) -> {
                        val content = "Successfully sent upgrade notice"
                        val invalidContent = ""
                        sendResponse(content, errorContent, invalidContent, sendOnlyErrorResponses, sendInvalidResponses)
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

    private fun readMessageFromRequest(request: HttpRequestData): Message =
        request.extract<PostConfirmationServicesRequest, Message> { it.message }

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
                        val content = BankAPIJsonMapper.mapBankToJson(requestedTrust)
                        val invalidContent = BankAPIJsonMapper.mapInvalidBankToJson()
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
                    request.url.encodedPath.startsWith(BankAPIJsonMapper.INVALID_BLOCKS_ENDPOINT) -> {
                        val blockIdentifier = readBlockIdentifierFromRequest(request)
                        val content = BankAPIJsonMapper.mapInvalidBlockToJson(blockIdentifier)
                        val invalidContent = BankAPIJsonMapper.mapInvalidResponseForInvalidBlocksRequest()
                        when {
                            enableErrorResponse -> respond(errorContent, InternalServerError, responseHeaders)
                            sendInvalidResponses -> respond(invalidContent, Accepted, responseHeaders)
                            else -> respond(content, Accepted, responseHeaders)
                        }
                    }
                    request.url.encodedPath.startsWith(BankAPIJsonMapper.BLOCKS_ENDPOINT) -> {
                        val balanceKey = readBalanceKeyFromRequest(request)
                        val content = BankAPIJsonMapper.mapBlockToJson(balanceKey)
                        val invalidContent = BankAPIJsonMapper.mapBlockResponseForBlockRequest()
                        when {
                            enableErrorResponse -> respond(errorContent, InternalServerError, responseHeaders)
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
