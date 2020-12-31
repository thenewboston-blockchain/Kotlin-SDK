package com.thenewboston.data.dto.bankapi.invalidblockdto.request


import com.thenewboston.data.dto.bankapi.common.request.PostRequest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



typealias PostInvalidBlockRequest = PostRequest<InvalidBlockMessage>

@Serializable
data class InvalidBlockMessage(

    @SerialName("block")
    val block: InvalidBlockMessageBlock,

    @SerialName("block_identifier")
    val blockIdentifier: String,

    @SerialName("primary_validator_node_identifier")
    val primaryValidatorNodeIdentifier: String,
)

@Serializable
data class InvalidBlockMessageBlock(

    @SerialName("account_number")
    val accountNumber: String,

    @SerialName("message")
    val message: InvalidBlockInnerMessage,

    @SerialName("signature")
    val signature: String,
)

@Serializable
data class InvalidBlockInnerMessage(

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
