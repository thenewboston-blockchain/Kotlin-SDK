package com.thenewboston.data.dto.bankapi.invalidblockdto

import com.thenewboston.utils.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvalidBlockList(

    @SerialName("count")
    val count: Long,

    @SerialName("next")
    val next: String?,

    @SerialName("previous")
    val previous: String?,

    @SerialName("results")
    val results: List<InvalidBlock>
)

@Serializable
data class InvalidBlock(

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
