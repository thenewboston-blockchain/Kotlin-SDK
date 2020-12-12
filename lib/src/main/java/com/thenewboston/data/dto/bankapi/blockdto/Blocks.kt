package com.thenewboston.data.dto.bankapi.blockdto

import com.thenewboston.utils.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockList(

    @SerialName("count")
    val count: Long,

    @SerialName("next")
    val next: String?,

    @SerialName("previous")
    val previous: String?,

    @SerialName("results")
    val blocks: List<Block>
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
