package com.thenewboston.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object BankAPIJsonMapper {

    const val VALIDATORS_ENDPOINT = "validators"
    const val SINGLE_VALIDATOR_ENDPOINT = VALIDATORS_ENDPOINT.plus("/6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53")
    const val CONFIG_ENDPOINT = "config"
    const val BANKS_ENDPOINT = "banks"
    const val ACCOUNTS_ENDPOINT = "accounts"
    const val BANK_TRANSACTIONS_ENDPOINT = "bank_transactions"
    const val BLOCKS_ENDPOINT = "blocks"

    fun mapBanksToJson(): String = Json.encodeToString(Mocks.banks())

    fun mapAccountsToJson(): String = Json.encodeToString(Mocks.accounts())

    fun mapBlocksToJson(): String = Json.encodeToString(Mocks.blocks())

    fun mapBankDetailToJson(): String = Json.encodeToString(Mocks.bankDetails())

    fun mapValidatorsToJson(): String = Json.encodeToString(Mocks.validators())

    fun mapValidatorToJson(): String = Json.encodeToString(Mocks.validator())

    fun mapBankTransactionsToJson(): String = Json.encodeToString(Mocks.bankTransactions())

}
