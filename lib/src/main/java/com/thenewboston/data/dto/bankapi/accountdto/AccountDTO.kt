package com.thenewboston.data.dto.bankapi.accountdto

import com.google.gson.annotations.SerializedName
<<<<<<< HEAD
import kotlinx.datetime.LocalDateTime
=======
import java.util.Date
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97

data class AccountDTO(
    val id: String,
    @SerializedName("created_date")
<<<<<<< HEAD
    val createdDate: LocalDateTime,
    @SerializedName("modified_date")
    val modifiedDate: LocalDateTime?,
=======
    val createdDate: Date,
    @SerializedName("modified_date")
    val modifiedDate: Date?,
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97
    @SerializedName("account_number")
    val accountNumber: String,
    val trust: Double
)
