package com.thenewboston.common.http.bankapi.accountdto

import com.google.gson.annotations.SerializedName
import java.util.*

data class AccountDTO(

    val id: String,
    @SerializedName("created_date")
    val createdDate: Date,
    @SerializedName("modified_date")
    val modifiedDate: Date,
    @SerializedName("account_number")
    val accountNumber: String,
    val trust: Double

)
