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

    fun mapPrimaryValidatorDetailsToJson(): String = Json.encodeToString(Mocks.primaryValidatorDetails())

    fun mapEmptyPrimaryValidatorDetailsToJson(): String = Json.encodeToString(Mocks.emptyPrimaryValidatorDetails())

    fun mapInternalServerErrorToJson(): String = Json.encodeToString(Mocks.internalServerError())
}
