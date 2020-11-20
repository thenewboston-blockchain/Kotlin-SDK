package com.thenewboston.data.dto.bankapi.accountdto

import com.thenewboston.data.dto.bankapi.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountListDTO(
    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<AccountDTO>
)

@Serializable
data class AccountDTO(
    @SerialName("id")
    val id: String,

    @Serializable(with = DateSerializer::class)
    @SerialName("created_date")
    val createdDate: LocalDateTime,

    @Serializable(with = DateSerializer::class)
    @SerialName("modified_date")
    val modifiedDate: LocalDateTime?,

    @SerialName("account_number")
    val accountNumber: String,

    @SerialName("trust")
    val trust: Double
)
