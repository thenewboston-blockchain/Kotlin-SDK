package com.thenewboston.data.dto.bankapi.confirmationblockdto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ConfirmationBlockDTO(
    val id: Int,
    @SerializedName("created_date")
    val createdDate: Date,
    @SerializedName("modified_date")
    val modifiedDate: Date?,
    @SerializedName("block_identifier")
    val blockIdentifier: String,
    val block: Int,
    val validator: Int
)
