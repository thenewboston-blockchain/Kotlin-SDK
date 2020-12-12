package com.thenewboston.data.dto.bankapi.accountdto.response

import com.thenewboston.utils.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountList(
    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<Account>
)

@Serializable
data class Account(
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
