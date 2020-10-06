package com.thenewboston.data.dto.bankapi.banktransactiondto

import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime

data class BlockDTO(
    val id: String,
    @SerializedName("created_date")
    val createdDate: LocalDateTime,
    @SerializedName("modified_date")
    val modifiedDate: LocalDateTime?,
    @SerializedName("balance_key")
    val balanceKey: String,
    val sender: String,
    val signature: String
)
