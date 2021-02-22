package com.thenewboston.utils.mapper

import com.thenewboston.utils.Mocks
import com.thenewboston.utils.Some
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ConfirmationValidatorAPIJsonMapper {

    const val CONFIRMATION_BLOCKS = "confirmation_blocks"

    const val VALID_CONFIRMATION_BLOCKS_ENDPOINT = CONFIRMATION_BLOCKS.plus("/${Some.blockIdentifier}/valid")

    const val QUEUED_CONFIRMATION_BLOCKS_ENDPOINT = CONFIRMATION_BLOCKS.plus("/${Some.blockIdentifier}/queued")

    fun mapInternalServerErrorToJson(): String = Json.encodeToString(Mocks.internalServerError())

    fun mapValidConfirmationBlocksToJson(): String = Json.encodeToString(Mocks.confirmationBlocks())

    fun mapQueuedConfirmationBlocksToJson(): String = Json.encodeToString(Mocks.confirmationBlocks())

    fun mapConfirmationBlockResponseToJson(): String = Json.encodeToString(Mocks.confirmationBlockMessage())
}
