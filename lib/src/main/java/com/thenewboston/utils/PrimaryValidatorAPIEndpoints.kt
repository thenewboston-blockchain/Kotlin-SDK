package com.thenewboston.utils

object PrimaryValidatorAPIEndpoints {
    const val ACCOUNTS_ENDPOINT = "/accounts"
    const val BANK_BLOCKS_ENDPOINT = "/bank_blocks"
    const val BANKS_ENDPOINT = "/banks"
    const val CONFIG_ENDPOINT = "/config"
    const val CONNECTION_REQUESTS_ENDPOINT = "/connection_requests"
    const val VALIDATORS_ENDPOINT = "/validators"

    fun accountsBalanceEndpoint(accountNumber: String) =
        "/accounts/$accountNumber/balance"

    fun accountsBalanceLockEndpoint(accountNumber: String) =
        "/accounts/$accountNumber/balance_lock"

    fun confirmationBlocksEndpoint(blockID: String) =
        "/confirmation_blocks/$blockID/valid"
}
