package com.thenewboston.data.dto.bankapi.invalidblockdto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostInvalidBlockRequest(

    @SerialName("message")
    val message: PostInvalidBlockMessage,

    @SerialName("node_identifier")
    val nodeIdentifier: String,

    @SerialName("signature")
    val signature: String,
)

@Serializable
data class PostInvalidBlockMessage(

    @SerialName("block")
    val block: PostInvalidBlockMessageBlock,

    @SerialName("block_identifier")
    val blockIdentifier: String,

    @SerialName("primary_validator_node_identifier")
    val primaryValidatorNodeIdentifier: String,
)

@Serializable
data class PostInvalidBlockMessageBlock(

    @SerialName("account_number")
    val accountNumber: String,

    @SerialName("message")
    val message: PostInvalidBlockMessageBlockMessage,

    @SerialName("signature")
    val signature: String,
)

@Serializable
data class PostInvalidBlockMessageBlockMessage(

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
