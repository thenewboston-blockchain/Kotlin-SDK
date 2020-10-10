package com.thenewboston.data.dto.bankapi.banktransactiondto

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
