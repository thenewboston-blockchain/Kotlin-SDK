package com.thenewboston.utils

import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.content.*
import kotlinx.serialization.json.Json

class PrimaryValidatorApiMockEngine {

    fun getSuccess() = getPrimaryMockEngine()
    fun getErrors() = getPrimaryMockEngine(sendOnlyErrorResponses = true)
    fun getEmptySuccess() = getPrimaryMockEngine(sendInvalidResponses = true)

    private val json = listOf(ContentType.Application.Json.toString())
    private val responseHeaders = headersOf("Content-Type" to json)

    private val mockHttpClient = MockHttpClientManager()

    private fun getPrimaryMockEngine(sendOnlyErrorResponses: Boolean = false, sendInvalidResponses: Boolean = false) =
        mockHttpClient.httpClient {
            val errorContent = PrimaryValidatorAPIJsonMapper.mapInternalServerErrorToJson()
            it.addHandler { request ->
                when (request.url.encodedPath) {
                    PrimaryValidatorAPIJsonMapper.CONFIG_ENDPOINT -> {
                        val content = PrimaryValidatorAPIJsonMapper.mapPrimaryValidatorDetailsToJson()
                        val emptyContent = PrimaryValidatorAPIJsonMapper.mapEmptyPrimaryValidatorDetailsToJson()
                        sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                    }
                    PrimaryValidatorAPIJsonMapper.VALIDATORS_ENDPOINT -> {
                        val offset = request.url.parameters.get("offset")?.toInt()
                        val limit = request.url.parameters.get("limit")?.toInt()
                        val content = PrimaryValidatorAPIJsonMapper.mapValidatorsToJson(offset, limit)
                        val emptyContent = PrimaryValidatorAPIJsonMapper.mapEmptyValidatorsToJson()
                        sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                    }
                    PrimaryValidatorAPIJsonMapper.SINGLE_VALIDATOR_ENDPOINT -> {
                        val content = PrimaryValidatorAPIJsonMapper.mapValidatorToJson()
                        val emptyContent = PrimaryValidatorAPIJsonMapper.mapEmptyValidatorToJson()
                        sendResponse(content, errorContent, emptyContent, sendOnlyErrorResponses, sendInvalidResponses)
                    }
                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }
                }
            }
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
}
