package com.thenewboston.utils.mapper

import com.thenewboston.utils.Mocks
import com.thenewboston.utils.PaginationOptions

import com.thenewboston.utils.Some
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ConfirmationValidatorAPIJsonMapper {

    const val BANK_CONFIRMATION_SERVICES_ENDPOINT = "bank_confirmation_services"

    const val CONFIRMATION_BLOCKS = "confirmation_blocks"

    const val UPGRADE_REQUEST_ENDPOINT = "upgrade_request"

    const val PRIMARY_VALIDATOR_UPDATED_ENDPOINT = "primary_validator_updated"

    const val VALID_CONFIRMATION_BLOCKS_ENDPOINT = CONFIRMATION_BLOCKS.plus("/${Some.blockIdentifier}/valid")

    const val QUEUED_CONFIRMATION_BLOCKS_ENDPOINT = CONFIRMATION_BLOCKS.plus("/${Some.blockIdentifier}/queued")

    fun mapBankConfirmationServicesToJson(offset: Int?, limit: Int?): String {
        return Json.encodeToString(Mocks.bankConfirmationServicesList(PaginationOptions(offset, limit)))
    }

    fun mapEmptyBankConfirmationServicesToJson(): String {
        return Json.encodeToString(Mocks.emptyConfirmationServicesList())
    }

    fun mapInternalServerErrorToJson(): String = Json.encodeToString(Mocks.internalServerError())

    fun mapValidConfirmationBlocksToJson(): String = Json.encodeToString(Mocks.confirmationBlocks())

    fun mapQueuedConfirmationBlocksToJson(): String = Json.encodeToString(Mocks.confirmationBlocks())

    fun mapConfirmationBlockResponseToJson(): String = Json.encodeToString(Mocks.confirmationBlockMessage())

    fun mapPrimaryValidatorValidatorDetailsToJson(): String =
        Json.encodeToString(Mocks.validatorDetails("PRIMARY_VALIDATOR"))

    fun mapEmptyValidatorDetailsToJson(): String =
        Json.encodeToString(Mocks.emptyValidatorDetails())
}
