package com.thenewboston.data.dto.bankapi.accountdto

import com.thenewboston.data.dto.bankapi.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class AccountDTO(
    @SerialName("id")
    val id: String,

    @Serializable(with = DateSerializer::class)
    @SerialName("created_date")
    val createdDate: Date,

    @Serializable(with = DateSerializer::class)
    @SerialName("modified_date")
    val modifiedDate: Date?,

    @SerialName("account_number")
    val accountNumber: String,

    @SerialName("trust")
    val trust: Double
)
