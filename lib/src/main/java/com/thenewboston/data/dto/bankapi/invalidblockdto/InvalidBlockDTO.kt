package com.thenewboston.data.dto.bankapi.invalidblockdto

import com.thenewboston.utils.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvalidBlockDTO(
    @SerialName("id")
    val id: String,

    @Serializable(with = DateSerializer::class)
    @SerialName("created_date")
    val createdDate: LocalDateTime,

    @Serializable(with = DateSerializer::class)
    @SerialName("modified_date")
    val modifiedDate: LocalDateTime?,

    @SerialName("block_identifier")
    val blockIdentifier: String,

    @SerialName("block")
    val block: String,

    @SerialName("confirmation_validator")
    val confirmationValidator: String,

    @SerialName("primary_validator")
    val primaryValidator: String,
)
@Serializable
data class InvalidBlockList(

    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<InvalidBlockDTO>
)
