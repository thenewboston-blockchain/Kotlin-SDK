package com.thenewboston.data.dto.primaryvalidatorapi.bankblockdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankBlock(
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("message")
    val message: BankBlockMessageBalance,
    @SerialName("signature")
    val signature: String
)

@Serializable
data class BankBlockMessageBalance(
    @SerialName("balance_key")
    val balanceKey: String,
    @SerialName("txs")
    val txs: List<BankBlockTx>
)

@Serializable
data class BankBlockTx(
    @SerialName("amount")
    val amount: Int,
    @SerialName("recipient")
    val recipient: String
)
