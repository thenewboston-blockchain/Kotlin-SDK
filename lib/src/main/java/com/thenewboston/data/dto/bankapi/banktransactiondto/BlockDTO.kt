package com.thenewboston.data.dto.bankapi.banktransactiondto

import com.thenewboston.data.dto.bankapi.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockDTO(
    @SerialName("id")
    val id: String,

    @Serializable(with = DateSerializer::class)
    @SerialName("created_date")
    val createdDate: LocalDateTime,

    @Serializable(with = DateSerializer::class)
    @SerialName("modified_date")
    val modifiedDate: LocalDateTime?,

    @SerialName("balance_key")
    val balanceKey: String,

    @SerialName("sender")
    val sender: String,

    @SerialName("signature")
    val signature: String
)
