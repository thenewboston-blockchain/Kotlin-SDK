package com.thenewboston.data.dto.bankapi.confirmationblockdto

import com.thenewboston.data.dto.bankapi.DateSerializer
import java.util.Date
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationBlockDTO(
    @SerialName("id")
    val id: Int,

    @Serializable(with = DateSerializer::class)
    @SerialName("created_date")
    val createdDate: Date,

    @Serializable(with = DateSerializer::class)
    @SerialName("modified_date")
    val modifiedDate: Date?,

    @SerialName("block_identifier")
    val blockIdentifier: String,

    @SerialName("block")
    val block: Int,

    @SerialName("validator")
    val validator: Int
)
