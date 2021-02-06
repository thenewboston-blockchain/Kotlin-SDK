package com.thenewboston.data.dto.common.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationBlocks(
    @SerialName("message")
    val message: ConfirmationBlockMessage,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("signature")
    val signature: String
)

@Serializable
data class ConfirmationBlockMessage(
    @SerialName("block")
    val block: ConfirmationBlock,
    @SerialName("block_identifier")
    val blockIdentifier: String,
    @SerialName("updated_balances")
    val updatedBalances: List<UpdatedBalance>
)

@Serializable
data class ConfirmationBlock(
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("message")
    val message: MessageBalance,
    @SerialName("signature")
    val signature: String
)

@Serializable
data class UpdatedBalance(
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("balance")
    val balance: Int,
    @SerialName("balance_lock")
    val balanceLock: String
)

@Serializable
data class MessageBalance(
    @SerialName("balance_key")
    val balanceKey: String,
    @SerialName("txs")
    val txs: List<Tx>
)

@Serializable
data class Tx(
    @SerialName("amount")
    val amount: Int,
    @SerialName("recipient")
    val recipient: String
)
