package com.thenewboston.data.dto.bankapi.validatordto

import com.google.gson.annotations.SerializedName

data class ValidatorDTO(
    @SerializedName("account_number")
    val accountNumber: String,
    @SerializedName("ip_address")
    val ipAddress: String,
    @SerializedName("node_identifier")
    val nodeIdentifier: String,
    val port: Int,
    val protocol: String,
    val version: String,
    @SerializedName("default_transaction_fee")
    val defaultTransactionFee: Double,
    @SerializedName("root_account_file")
    val rootAccountFile: String,
    @SerializedName("root_account_file_hash")
    val rootAccountFileHash: String,
    @SerializedName("seed_block_identifier")
    val seedBlockIdentifier: String,
    @SerializedName("daily_confirmation_rate")
    val dailyConfirmationRate: Double? = null,
    val trust: Double
)
