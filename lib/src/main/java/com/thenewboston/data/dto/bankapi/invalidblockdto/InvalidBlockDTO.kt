package com.thenewboston.data.dto.bankapi.invalidblockdto

import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime

data class InvalidBlockDTO(
    val id: String,
    @SerializedName("created_date")
    val createdDate: LocalDateTime,
    @SerializedName("modified_date")
    val modifiedDate: LocalDateTime?,
    @SerializedName("block_identifier")
    val blockIdentifier: String,
    val block: String,
    @SerializedName("confirmation_validator")
    val confirmationValidator: String,
    @SerializedName("primary_validator")
    val primaryValidator: String
)
