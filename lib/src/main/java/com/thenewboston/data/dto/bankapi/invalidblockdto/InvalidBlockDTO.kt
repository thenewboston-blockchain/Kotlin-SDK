package com.thenewboston.data.dto.bankapi.invalidblockdto

import com.google.gson.annotations.SerializedName
import java.util.*

data class InvalidBlockDTO(
    val id: String,
    @SerializedName("created_date")
    val createdDate: Date,
    @SerializedName("modified_date")
    val modifiedDate: Date,
    @SerializedName("block_identifier")
    val blockIdentifier: String,
    val block: String,
    @SerializedName("confirmation_validator")
    val confirmationValidator: String,
    @SerializedName("primary_validator")
    val primaryValidator: String
)
