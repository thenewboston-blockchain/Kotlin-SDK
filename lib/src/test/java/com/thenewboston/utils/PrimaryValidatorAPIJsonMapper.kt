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
    const val SINGLE_VALIDATOR_ENDPOINT = VALIDATORS_ENDPOINT
        .plus("/6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53")

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

    fun mapValidatorsToJson(offset: Int?, limit: Int?): String {
        return Json.encodeToString(Mocks.validators(PaginationOptions(offset, limit)))
    }

    fun mapEmptyValidatorsToJson(): String = Json.encodeToString(Mocks.emptyValidators())

    fun mapValidatorToJson(): String = Json.encodeToString(Mocks.validator())

    fun mapEmptyValidatorToJson(): String = Json.encodeToString(Mocks.emptyValidator())

    fun mapInternalServerErrorToJson(): String = Json.encodeToString(Mocks.internalServerError())
}
