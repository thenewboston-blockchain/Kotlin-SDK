package com.thenewboston.data.dto.bankapi.accountdto

import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime


data class AccountDTO(
    val id: String,
    @SerializedName("created_date")
    val createdDate: LocalDateTime,
    @SerializedName("modified_date")
    val modifiedDate: LocalDateTime?,
    @SerializedName("account_number")
    val accountNumber: String,
    val trust: Double
)
