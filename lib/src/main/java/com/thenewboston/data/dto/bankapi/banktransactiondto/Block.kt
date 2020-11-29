package com.thenewboston.data.dto.bankapi.banktransactiondto

import com.thenewboston.utils.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockList(
    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<Block>
)

@Serializable
data class Block(
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
