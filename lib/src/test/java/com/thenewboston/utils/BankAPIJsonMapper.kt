package com.thenewboston.utils

import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request.Message
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object BankAPIJsonMapper {

    const val VALIDATORS_ENDPOINT = "validators"
    const val SINGLE_VALIDATOR_ENDPOINT = VALIDATORS_ENDPOINT
        .plus("/6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53")
    const val CONFIG_ENDPOINT = "config"
    const val BANKS_ENDPOINT = "banks"
    const val BANKS_TRUST_ENDPOINT = BANKS_ENDPOINT
        .plus("/35f4c988f425809ca7f5d0b319cdf8f7d7aba1b064fd0efc85d61fa0f4d05145")
    const val ACCOUNTS_ENDPOINT = "accounts"
    const val BANK_TRANSACTIONS_ENDPOINT = "bank_transactions"
    const val BLOCKS_ENDPOINT = "blocks"
    const val INVALID_BLOCKS_ENDPOINT = "invalid_blocks"
    const val VALIDATOR_CONFIRMATION_SERVICES_ENDPOINT = "validator_confirmation_services"

    fun mapBanksToJson(): String = Json.encodeToString(Mocks.banks())

    fun mapEmptyBanksToJson(): String = Json.encodeToString(Mocks.emptyBanks())

    fun mapAccountsToJson(): String = Json.encodeToString(Mocks.accounts())

    fun mapEmptyAccountsToJson(): String = Json.encodeToString(Mocks.emptyAccounts())

    fun mapAccountToJson(trust: Double): String = Json.encodeToString(Mocks.account(trust))

    fun mapEmptyAccountToJson(): String = Json.encodeToString(Mocks.emptyAccount())

    fun mapBlocksToJson(): String = Json.encodeToString(Mocks.blocks())

    fun mapEmptyBlocksToJson(): String = Json.encodeToString(Mocks.emptyBlocks())

    fun mapBankDetailToJson(): String = Json.encodeToString(Mocks.bankDetails())

    fun mapEmptyBankDetailToJson(): String = Json.encodeToString(Mocks.emptyBankDetails())

    fun mapValidatorsToJson(): String = Json.encodeToString(Mocks.validators())

    fun mapEmptyValidatorsToJson(): String = Json.encodeToString(Mocks.emptyValidators())

    fun mapValidatorToJson(): String = Json.encodeToString(Mocks.validator())

    fun mapEmptyValidatorToJson(): String = Json.encodeToString(Mocks.emptyValidator())

    fun mapBankTransactionsToJson(): String = Json.encodeToString(Mocks.bankTransactions())

    fun mapEmptyBankTransactionsToJson(): String = Json.encodeToString(Mocks.emptyBankTransactions())

    fun mapInternalServerErrorToJson(): String = Json.encodeToString(Mocks.internalServerError())

    fun mapBankToJson(trust: Double): String = Json.encodeToString(Mocks.bank(trust))

    fun mapInvalidBankToJson(): String = Json.encodeToString(Mocks.emptyBank())

    fun mapInvalidBlocksToJson(): String = Json.encodeToString(Mocks.invalidBlocks())

    fun mapEmptyInvalidBlocksToJson(): String = Json.encodeToString(Mocks.emptyInvalidBlocks())

    fun mapInvalidBlockToJson(blockIdentifier: String): String = Json.encodeToString(Mocks.invalidBlock(blockIdentifier))

    fun mapInvalidResponseForInvalidBlocksRequest(): String = Json.encodeToString(Mocks.emptyInvalidBlock())

    fun mapBlockToJson(balanceKey: String): String = Json.encodeToString(Mocks.postBlock(balanceKey))

    fun mapBlockResponseForBlockRequest(): String = Json.encodeToString(Mocks.emptyBlock())

    fun mapValidatorConfirmationServicesToJson(): String = Json.encodeToString(Mocks.confirmationServicesList())

    fun mapValidatorConfirmationServiceToJson(message: Message): String = Json.encodeToString(Mocks.confirmationServiceWithMessage(message))

    fun mapEmptyValidatorConfirmationServicesToJson(): String = Json.encodeToString(Mocks.emptyConfirmationServicesList())

    fun mapEmptyValidatorConfirmationServiceToJson(): String = Json.encodeToString(Mocks.emptyConfirmationServices())
}
