package com.thenewboston.data.dto.primaryvalidatorapi.confirmationblocksdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationBlock(

    @SerialName("message")
    val message: ConfirmationBlockMessage,

    @SerialName("node_identifier")
    val nodeIdentifier: String,

    @SerialName("signature")
    val signature: String,
)

@Serializable
data class ConfirmationBlockMessage(

    @SerialName("block")
    val block: ConfirmationBlockMessageBlock,

    @SerialName("block_identifier")
    val blockIdentifier: String,

    @SerialName("updated_balances")
    val updatedBalances: List<UpdatedBalance>,
)

@Serializable
data class ConfirmationBlockMessageBlock(

    @SerialName("account_number")
    val accountNumber: String,

    @SerialName("message")
    val message: ConfirmationBlockInnerMessage,

    @SerialName("signature")
    val signature: String,
)

@Serializable
data class ConfirmationBlockInnerMessage(

    @SerialName("balance_key")
    val balanceKey: String,

    @SerialName("txs")
    val transactions: List<Transaction>,
)

@Serializable
data class Transaction(

    @SerialName("amount")
    val amount: Double,

    @SerialName("recipient")
    val recipient: String,
)

@Serializable
data class UpdatedBalance(

    @SerialName("account_number")
    val accountNumber: String,

    @SerialName("balance")
    val balance: String,
)
