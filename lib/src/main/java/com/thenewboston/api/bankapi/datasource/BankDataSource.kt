package com.thenewboston.api.bankapi.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.config.BankConfig
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.data.dto.bankapi.accountdto.AccountList
import com.thenewboston.data.dto.bankapi.bankdto.BankList
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.banktransactiondto.BlockList
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlockList
import com.thenewboston.data.dto.bankapi.validatordto.Validator
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorList
import com.thenewboston.utils.Endpoints
import io.ktor.client.request.*
import io.ktor.util.*
import io.ktor.utils.io.errors.*
import javax.inject.Inject

@KtorExperimentalAPI
class BankDataSource @Inject constructor(private val networkClient: NetworkClient) {

    suspend fun fetchBanks() = makeApiCall(
        call = { banks() },
        errorMessage = "Failed to retrieve banks"
    )

    private suspend fun banks(): Outcome<BankList> {
        val result = networkClient.defaultClient.get<BankList>(Endpoints.BANKS_ENDPOINT)

        return when {
            result.banks.isNullOrEmpty() -> Outcome.Error("Error fetching banks", IOException())
            else -> Outcome.Success(result)
        }
    }

    suspend fun fetchBankDetails(bankConfig: BankConfig) = makeApiCall(
        call = { bankDetail(bankConfig) },
        errorMessage = "Failed to retrieve bank details"
    )

    private suspend fun bankDetail(bankConfig: BankConfig): Outcome<BankDetails> {
        val result = networkClient.newClient(
            ipAddress = bankConfig.ipAddress,
            port = bankConfig.port
        ).get<BankDetails>(Endpoints.CONFIG_ENDPOINT)

        return Outcome.Success(result)
    }

    suspend fun fetchBankTransactions() = makeApiCall(
        call = { bankTransactions() },
        errorMessage = "Failed to retrieve bank transactions"
    )

    private suspend fun bankTransactions(): Outcome<BankTransactionList> {
        val endpoint = Endpoints.BANK_TRANSACTIONS_ENDPOINT
        val result = networkClient.defaultClient.get<BankTransactionList>(endpoint)

        return when {
            result.bankTransactions.isNullOrEmpty() ->
                Outcome.Error("Error bank transactions", java.io.IOException())
            else -> Outcome.Success(result)
        }
    }

    suspend fun fetchValidators(): Outcome<ValidatorList> = makeApiCall(
        call = { doFetchValidators() },
        errorMessage = "Could not fetch list of validators"
    )

    private suspend fun doFetchValidators(): Outcome<ValidatorList> {
        val endpoint = Endpoints.VALIDATORS_ENDPOINT
        val validators = networkClient.defaultClient.get<ValidatorList>(endpoint)

        return when {
            validators.results.isNullOrEmpty() -> Outcome.Error("Received null or empty list", null)
            else -> Outcome.Success(validators)
        }
    }

    suspend fun fetchValidator(nodeIdentifier: String): Outcome<Validator> = makeApiCall(
        call = { doFetchValidator(nodeIdentifier) },
        errorMessage = "Could not fetch validator with NID $nodeIdentifier"
    )

    private suspend fun doFetchValidator(nodeIdentifier: String): Outcome<Validator> {
        val validatorsEndpoint = Endpoints.VALIDATORS_ENDPOINT
        val urlSuffix = "$validatorsEndpoint/$nodeIdentifier"
        val validator = networkClient.defaultClient.get<Validator>(urlSuffix)

        return Outcome.Success(validator)
    }

    suspend fun fetchAccounts(): Outcome<AccountList> = makeApiCall(
        call = { accounts() },
        errorMessage = "Could not fetch list of accounts"
    )

    private suspend fun accounts(): Outcome<AccountList> {
        val accounts = networkClient.defaultClient.get<AccountList>(Endpoints.ACCOUNTS_ENDPOINT)

        return when {
            accounts.results.isNullOrEmpty() -> Outcome.Error(
                "Received null or empty list",
                IOException()
            )
            else -> Outcome.Success(accounts)
        }
    }

    suspend fun fetchBlocks(): Outcome<BlockList> = makeApiCall(
        call = { blocks() },
        errorMessage = "Could not fetch list of blocks"
    )

    private suspend fun blocks(): Outcome<BlockList> {
        val blocks = networkClient.defaultClient.get<BlockList>(Endpoints.BLOCKS_ENDPOINT)

        return when {
            blocks.results.isNullOrEmpty() -> Outcome.Error(
                "Received null or empty list",
                IOException()
            )
            else -> Outcome.Success(blocks)
        }
    }

    suspend fun fetchInvalidBlocks(): Outcome<InvalidBlockList> = makeApiCall(
        call = { getInvalidBlocks() },
        errorMessage = "Could not fetch list of invalid blocks"
    )

    private suspend fun getInvalidBlocks(): Outcome<InvalidBlockList> {
        val invalidBlocks = networkClient.defaultClient.get<InvalidBlockList>(Endpoints.INVALID_BLOCKS_ENDPOINT)

        return when {
            invalidBlocks.results.isNullOrEmpty() -> Outcome.Error(
                "Received null or empty list",
                IOException()
            )
            else -> Outcome.Success(invalidBlocks)
        }
    }
}
