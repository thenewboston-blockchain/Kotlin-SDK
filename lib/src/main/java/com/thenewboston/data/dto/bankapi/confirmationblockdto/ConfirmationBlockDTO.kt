package com.thenewboston.data.dto.bankapi.confirmationblockdto

import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime

data class ConfirmationBlockDTO(
    val id: Int,
    @SerializedName("created_date")
    val createdDate: LocalDateTime,
    @SerializedName("modified_date")
    val modifiedDate: LocalDateTime?,
    @SerializedName("block_identifier")
    val blockIdentifier: String,
    val block: Int,
    val validator: Int
)
