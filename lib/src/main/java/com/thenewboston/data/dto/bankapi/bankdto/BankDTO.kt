package com.thenewboston.data.dto.bankapi.bankdto

import com.google.gson.annotations.SerializedName

data class BankDTO(
    @SerializedName("account_number")
    val accountNumber: String,
    @SerializedName("ip_address")
    val ipAddress: String,
    @SerializedName("node_identifier")
    val nodeIdentifier: String,
    val port: Int? = null,
    val protocol: String,
    val version: String,
    @SerializedName("default_transaction_fee")
    val defaultTransactionFee: Double,
    val trust: Double
)
