package com.thenewboston.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PrimaryValidatorAPIJsonMapper {

    const val ACCOUNTS_ENDPOINT = "accounts"
    const val BANK_BLOCKS_ENDPOINT = "bank_blocks"
    const val BANKS_ENDPOINT = "banks"
    const val SINGLE_BANKS_ENDPOINT = "banks"
        .plus("/d5356888dc9303e44ce52b1e06c3165a7759b9df1e6a6dfbd33ee1c3df1ab4d1")
    const val CONFIG_ENDPOINT = "config"
    const val CONFIRMATION_BLOCKS = "confirmation_blocks"
    const val CONNECTION_REQUESTS_ENDPOINT = "connection_requests"
    const val VALIDATORS_ENDPOINT = "validators"

    fun mapBankFromValidatorToJson(): String {
        return Json.encodeToString(Mocks.bankFromValidator())
    }

    fun mapEmptyBankFromValidatorToJson(): String = Json.encodeToString(Mocks.emptyBankFromValidator())

    fun mapBanksFromValidatorToJson(offset: Int?, limit: Int?): String {
        return Json.encodeToString(Mocks.banksFromValidator(PaginationOptions(offset, limit)))
    }

    fun mapEmptyBanksFromValidatorToJson(): String = Json.encodeToString(Mocks.emptyBanksFromValidator())

    fun mapPrimaryValidatorDetailsToJson(): String = Json.encodeToString(Mocks.primaryValidatorDetails())

    fun mapEmptyPrimaryValidatorDetailsToJson(): String = Json.encodeToString(Mocks.emptyPrimaryValidatorDetails())

    fun mapInternalServerErrorToJson(): String = Json.encodeToString(Mocks.internalServerError())
}
