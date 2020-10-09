package com.thenewboston.data.dto.bankapi.invalidblockdto

import com.thenewboston.data.dto.bankapi.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class InvalidBlockDTO(
    @SerialName("id")
    val id: String,

    @Serializable(with = DateSerializer::class)
    @SerialName("created_date")
    val createdDate: Date,

    @Serializable(with = DateSerializer::class)
    @SerialName("modified_date")
    val modifiedDate: Date?,

    @SerialName("block_identifier")
    val blockIdentifier: String,

    @SerialName("block")
    val block: String,

    @SerialName("confirmation_validator")
    val confirmationValidator: String,

    @SerialName("primary_validator")
    val primaryValidator: String
)
