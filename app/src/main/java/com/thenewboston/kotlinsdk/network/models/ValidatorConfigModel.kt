package com.thenewboston.kotlinsdk.network.models


import com.google.gson.annotations.SerializedName

data class ValidatorConfigModel(
    @SerializedName("account_number")
    val accountNumber: String?, // acc_no
    @SerializedName("daily_confirmation_rate")
    val dailyConfirmationRate: Int?, //
    @SerializedName("default_transaction_fee")
    val defaultTransactionFee: Int?, // 1
    @SerializedName("ip_address")
    val ipAddress: String?, // 54.183.17.227
    @SerializedName("node_identifier")
    val nodeIdentifier: String?, //
    @SerializedName("node_type")
    val nodeType: String?, // PRIMARY_VALIDATOR
    val port: Int?, // 80
    @SerializedName("primary_validator")
    val primaryValidator: String?,
    val protocol: String?, // http
    @SerializedName("root_account_file")
    val rootAccountFile: String?, // http://54.183.17.224/.....json
    @SerializedName("root_account_file_hash")
    val rootAccountFileHash: String?, // hash
    @SerializedName("seed_block_identifier")
    val seedBlockIdentifier: String?,
    val version: String? // v1.0
)
