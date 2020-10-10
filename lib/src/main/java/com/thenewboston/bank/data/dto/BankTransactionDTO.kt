package com.thenewboston.bank.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankTransactionDTO(
    @SerialName("id")
    val id: String,

    @SerialName("block")
    val block: BlockDTO,

    @SerialName("amount")
    val amount: Double,

    @SerialName("recipient")
    val recipient: String
)
