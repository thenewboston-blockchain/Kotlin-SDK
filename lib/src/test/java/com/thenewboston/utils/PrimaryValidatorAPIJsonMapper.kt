package com.thenewboston.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PrimaryValidatorAPIJsonMapper {

    const val ACCOUNTS_ENDPOINT = "accounts"
    const val BANK_BLOCKS_ENDPOINT = "bank_blocks"
    const val BANKS_ENDPOINT = "banks"
    const val CONFIG_ENDPOINT = "config"
    const val CONFIRMATION_BLOCKS = "confirmation_blocks"
    const val CONNECTION_REQUESTS_ENDPOINT = "connection_requests"
    const val VALIDATORS_ENDPOINT = "validators"
    const val SINGLE_VALIDATOR_ENDPOINT = VALIDATORS_ENDPOINT
        .plus("/6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53")

    fun mapPrimaryValidatorDetailsToJson(): String = Json.encodeToString(Mocks.primaryValidatorDetails())

    fun mapEmptyPrimaryValidatorDetailsToJson(): String = Json.encodeToString(Mocks.emptyPrimaryValidatorDetails())

    fun mapValidatorsToJson(offset: Int?, limit: Int?): String {
        return Json.encodeToString(Mocks.validators(PaginationOptions(offset, limit)))
    }

    fun mapEmptyValidatorsToJson(): String = Json.encodeToString(Mocks.emptyValidators())

    fun mapValidatorToJson(): String = Json.encodeToString(Mocks.validator())

    fun mapEmptyValidatorToJson(): String = Json.encodeToString(Mocks.emptyValidator())

    fun mapInternalServerErrorToJson(): String = Json.encodeToString(Mocks.internalServerError())
}
