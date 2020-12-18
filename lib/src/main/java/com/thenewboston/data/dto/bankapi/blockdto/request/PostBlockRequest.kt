package com.thenewboston.data.dto.bankapi.blockdto.request

import com.thenewboston.data.dto.bankapi.invalidblockdto.request.Transaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostBlockRequest(

    @SerialName("account_number")
    val accountNumber: String,

    @SerialName("message")
    val message: BlockMessage,

    @SerialName("signature")
    val signature: String
)

@Serializable
data class BlockMessage(

    @SerialName("balance_key")
    val balanceKey: String,

    @SerialName("txs")
    val transactions: List<Transaction>
)
