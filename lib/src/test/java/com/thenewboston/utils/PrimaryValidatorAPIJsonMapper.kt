package com.thenewboston.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PrimaryValidatorAPIJsonMapper {

    const val ACCOUNTS_ENDPOINT = "accounts"
    const val ACCOUNT_BALANCE_ENDPOINT = ACCOUNTS_ENDPOINT
        .plus("/" + Some.accountNumber + "/balance")
    const val ACCOUNT_BALANCE_LOCK_ENDPOINT = ACCOUNTS_ENDPOINT
        .plus("/" + Some.accountNumber + "/balance_lock")
    const val BANK_BLOCKS_ENDPOINT = "bank_blocks"
    const val BANKS_ENDPOINT = "banks"
    const val CONFIG_ENDPOINT = "config"
    const val CONFIRMATION_BLOCKS = "confirmation_blocks"
    const val CONNECTION_REQUESTS_ENDPOINT = "connection_requests"
    const val VALIDATORS_ENDPOINT = "validators"

    fun mapPrimaryValidatorDetailsToJson(): String = Json.encodeToString(Mocks.primaryValidatorDetails())

    fun mapEmptyPrimaryValidatorDetailsToJson(): String = Json.encodeToString(Mocks.emptyPrimaryValidatorDetails())

    fun mapAccountsFromValidatorToJson(offset: Int?, limit: Int?): String =
        Json.encodeToString(Mocks.accountsFromValidator(PaginationOptions(offset, limit)))

    fun mapEmptyAccountsFromValidatorToJson(): String = Json.encodeToString(Mocks.emptyAccountsFromValidator())

    fun mapAccountBalanceToJson(): String = Json.encodeToString(Mocks.accountBalance())

    fun mapEmptyAccountBalanceToJson(): String = Json.encodeToString(Mocks.emptyAccountBalance())

    fun mapAccountBalanceLockToJson(): String = Json.encodeToString(Mocks.accountBalanceLock())

    fun mapEmptyAccountBalanceLockToJson(): String = Json.encodeToString(Mocks.emptyAccountBalanceLock())

    fun mapInternalServerErrorToJson(): String = Json.encodeToString(Mocks.internalServerError())
}
