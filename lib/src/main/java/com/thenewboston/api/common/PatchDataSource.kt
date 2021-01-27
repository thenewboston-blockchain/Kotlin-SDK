package com.thenewboston.api.common

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.accountdto.response.Account
import com.thenewboston.data.dto.bankapi.blockdto.Block
import com.thenewboston.data.dto.bankapi.blockdto.request.PostBlockRequest
import com.thenewboston.data.dto.common.request.UpdateTrustRequest
import com.thenewboston.data.dto.common.response.Bank
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlock
import com.thenewboston.data.dto.bankapi.invalidblockdto.request.PostInvalidBlockRequest
import com.thenewboston.utils.BankAPIEndpoints
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import javax.inject.Inject

class PatchDataSource @Inject constructor(private val networkClient: NetworkClient) {

    suspend fun doUpdateAccount(accountNumber: String, request: UpdateTrustRequest): Outcome<Account> {
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

    suspend fun doUpdateBankTrust(request: UpdateTrustRequest): Outcome<Bank> {
        val url = "${BankAPIEndpoints.BANKS_ENDPOINT}/${request.nodeIdentifier}"

        val response = networkClient.defaultClient.patch<Bank> {
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

    suspend fun doSendInvalidBlock(request: PostInvalidBlockRequest): Outcome<InvalidBlock> {
        val response = networkClient.defaultClient.patch<InvalidBlock> {
            url(BankAPIEndpoints.INVALID_BLOCKS_ENDPOINT)
            body = request
        }

        return when {
            response.blockIdentifier.isBlank() -> {
                val blockIdentifier = request.message.blockIdentifier
                val message = "Received invalid response when sending invalid block with identifier $blockIdentifier"
                Outcome.Error(message, IOException())
            }
            else -> Outcome.Success(response)
        }
    }

    suspend fun doSendBlock(request: PostBlockRequest): Outcome<Block> {
        val response = networkClient.defaultClient.patch<Block> {
            url(BankAPIEndpoints.BLOCKS_ENDPOINT)
            body = request
        }

        return when {
            response.balanceKey.isBlank() -> {
                val balanceKey = request.message.balanceKey
                val message = "Received invalid response when sending block with balance key: $balanceKey"
                Outcome.Error(message, IOException())
            }
            else -> Outcome.Success(response)
        }
    }
}
