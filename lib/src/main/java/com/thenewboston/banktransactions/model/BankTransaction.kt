package com.thenewboston.banktransactions.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankTransaction(
    @SerialName("id")
    val id: String,
    @SerialName("block")
    val block: BankBlock,
    @SerialName("recipient")
    val recipient: String,
    @SerialName("amount")
    val amount: Int
)
