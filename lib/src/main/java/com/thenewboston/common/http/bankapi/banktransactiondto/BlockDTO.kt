package com.thenewboston.common.http.bankapi.banktransactiondto

import com.google.gson.annotations.SerializedName
import java.util.*

data class BlockDTO(

    val id: String,
    @SerializedName("created_date")
    val createdDate: Date,
    @SerializedName("modified_date")
    val modifiedDate: Date,
    @SerializedName("balance_key")
    val balanceKey: String,
    val sender: String,
    val signature: String


)
