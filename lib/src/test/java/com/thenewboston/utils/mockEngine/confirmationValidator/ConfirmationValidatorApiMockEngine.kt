package com.thenewboston.utils.mockEngine.confirmationValidator

import com.thenewboston.utils.mapper.ConfirmationValidatorAPIJsonMapper
import com.thenewboston.utils.mockEngine.manager.MockHttpClientManager
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.InternalServerError

class ConfirmationValidatorApiMockEngine {
    fun getSuccess() = getConfirmationMockEngine()
    fun getErrors() = getConfirmationMockEngine(sendOnlyErrorResponses = true)
    fun getEmptySuccess() = getConfirmationMockEngine(sendInvalidResponses = true)

    private val json = listOf(ContentType.Application.Json.toString())
    private val responseHeaders = headersOf("Content-Type" to json)

    private val mockHttpClient = MockHttpClientManager()

    private fun getConfirmationMockEngine(sendOnlyErrorResponses: Boolean = false, sendInvalidResponses: Boolean = false) =
        mockHttpClient.httpClient {
            val errorContent = ConfirmationValidatorAPIJsonMapper.mapInternalServerErrorToJson()
            it.addHandler { request ->
                when (request.url.encodedPath) {
                    ConfirmationValidatorAPIJsonMapper.VALID_CONFIRMATION_BLOCKS_ENDPOINT -> {
                        val content = ConfirmationValidatorAPIJsonMapper.mapValidConfirmationBlocksToJson()
                        sendResponse(content, errorContent, "", sendOnlyErrorResponses, sendInvalidResponses)
                    }
                    ConfirmationValidatorAPIJsonMapper.QUEUED_CONFIRMATION_BLOCKS_ENDPOINT -> {
                        val content = ConfirmationValidatorAPIJsonMapper.mapQueuedConfirmationBlocksToJson()
                        sendResponse(content, errorContent, "", sendOnlyErrorResponses, sendInvalidResponses)
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
