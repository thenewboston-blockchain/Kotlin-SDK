package com.thenewboston.api.bankapi.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.data.dto.bankapi.accountdto.response.Account
import com.thenewboston.data.dto.bankapi.accountdto.response.AccountList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankTrustResponse
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.banktransactiondto.BlockList
import com.thenewboston.data.dto.bankapi.common.request.UpdateTrustRequest
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlockList
import com.thenewboston.data.dto.bankapi.validatordto.Validator
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorList
import com.thenewboston.utils.BankAPIEndpoints
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.KtorExperimentalAPI
import io.ktor.utils.io.errors.IOException
import javax.inject.Inject

@KtorExperimentalAPI
class BankDataSource @Inject constructor(private val networkClient: NetworkClient) {

    suspend fun fetchBanks() = makeApiCall(
        call = { banks() },
        errorMessage = "Failed to retrieve banks"
    )

    private suspend fun banks(): Outcome<BankList> {
        val result = networkClient.defaultClient.get<BankList>(BankAPIEndpoints.BANKS_ENDPOINT)

        return when {
            result.banks.isNullOrEmpty() -> Outcome.Error("Error fetching banks", IOException())
            else -> Outcome.Success(result)
        }
    }

    suspend fun fetchBankDetails() = makeApiCall(
        call = { bankDetail() },
        errorMessage = "Failed to retrieve bank details"
    )

    private suspend fun bankDetail(): Outcome<BankDetails> {
        val result = networkClient.defaultClient.get<BankDetails>(BankAPIEndpoints.CONFIG_ENDPOINT)

        return Outcome.Success(result)
    }

    suspend fun fetchBankTransactions() = makeApiCall(
        call = { bankTransactions() },
        errorMessage = "Failed to retrieve bank transactions"
    )

    private suspend fun bankTransactions(): Outcome<BankTransactionList> {
        val endpoint = BankAPIEndpoints.BANK_TRANSACTIONS_ENDPOINT
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
        val endpoint = BankAPIEndpoints.VALIDATORS_ENDPOINT
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
        val validatorsEndpoint = BankAPIEndpoints.VALIDATORS_ENDPOINT
        val urlSuffix = "$validatorsEndpoint/$nodeIdentifier"
        val validator = networkClient.defaultClient.get<Validator>(urlSuffix)

        return Outcome.Success(validator)
    }

    suspend fun fetchAccounts(): Outcome<AccountList> = makeApiCall(
        call = { accounts() },
        errorMessage = "Could not fetch list of accounts"
    )

    private suspend fun accounts(): Outcome<AccountList> {
        val urlString = BankAPIEndpoints.ACCOUNTS_ENDPOINT
        val accounts = networkClient.defaultClient.get<AccountList>(urlString)

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
        val blocks = networkClient.defaultClient.get<BlockList>(BankAPIEndpoints.BLOCKS_ENDPOINT)

        return when {
            blocks.results.isNullOrEmpty() -> Outcome.Error(
                "Received null or empty list",
                IOException()
            )
            else -> Outcome.Success(blocks)
        }
    }

    suspend fun updateAccountTrust(accountNumber: String, request: UpdateTrustRequest): Outcome<Account> = makeApiCall(
        call = { doUpdateAccount(accountNumber, request) },
        errorMessage = "Could not update trust level of given account"
    )

    private suspend fun doUpdateAccount(accountNumber: String, request: UpdateTrustRequest): Outcome<Account> {
        val patchAccountUrl = "${BankAPIEndpoints.ACCOUNTS_ENDPOINT}/$accountNumber"

        val updatedAccount = networkClient.defaultClient.patch<Account> {
            url(patchAccountUrl)
            contentType(ContentType.Application.Json)
            body = request
        }

        return when {
            updatedAccount.id.isBlank() -> Outcome.Error(
                "Received unexpected response when updating trust level of account $accountNumber",
                IOException()
            )
            else -> Outcome.Success(updatedAccount)
        }
    }

    suspend fun updateBankTrust(request: UpdateTrustRequest): Outcome<BankTrustResponse> = makeApiCall(
        call = { doUpdateBankTrust(request) },
        errorMessage = "Could not send bank trust for ${request.nodeIdentifier}"
    )

    private suspend fun doUpdateBankTrust(request: UpdateTrustRequest): Outcome<BankTrustResponse> {
        val url = "${BankAPIEndpoints.BANKS_ENDPOINT}/${request.nodeIdentifier}"

        val response = networkClient.defaultClient.patch<BankTrustResponse> {
            url(url)
            body = request
        }

        return when {
            response.accountNumber.isBlank() -> {
                val message = "Received invalid request when updating trust level of bank with" +
                    " ${request.nodeIdentifier}"
                Outcome.Error(message, IOException())
            }
            else -> {
                Outcome.Success(response)
            }
        }
    }

    suspend fun fetchInvalidBlocks(): Outcome<InvalidBlockList> = makeApiCall(
        call = { getInvalidBlocks() },
        errorMessage = "Could not fetch list of invalid blocks"
    )

    private suspend fun getInvalidBlocks(): Outcome<InvalidBlockList> {
        val invalidBlocks = networkClient.defaultClient
            .get<InvalidBlockList>(BankAPIEndpoints.INVALID_BLOCKS_ENDPOINT)

        return when {
            invalidBlocks.results.isNullOrEmpty() -> Outcome.Error(
                "No invalid blocks are available at this time",
                IOException()
            )
            else -> Outcome.Success(invalidBlocks)
        }
    }
}
