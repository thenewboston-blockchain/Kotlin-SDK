package com.thenewboston.kotlinsdk.network.models


import com.google.gson.annotations.SerializedName

data class BankConfigModel(
    var nConfServices: Int?,
    @SerializedName("account_number")
    val accountNumber: String, // acc_no
    @SerializedName("default_transaction_fee")
    val defaultTransactionFee: Int?, // 1
    @SerializedName("ip_address")
    val ipAddress: String, // 143.110.137.54
    @SerializedName("node_identifier")
    val nodeIdentifier: String, // node_id
    @SerializedName("node_type")
    val nodeType: String, // BANK
    val port: Int?, // 80
    @SerializedName("primary_validator")
    val primaryValidator: PrimaryValidator,
    val protocol: String, // http
    val version: String // v1.0
) {
    data class PrimaryValidator(
        @SerializedName("account_number")
        val accountNumber: String, // acc_no
        @SerializedName("daily_confirmation_rate")
        val dailyConfirmationRate: Any?, // null
        @SerializedName("default_transaction_fee")
        val defaultTransactionFee: Int, // 1
        @SerializedName("ip_address")
        val ipAddress: String, // 54.183.17.224
        @SerializedName("node_identifier")
        val nodeIdentifier: String, // node_id
        val port: Any?, // null
        val protocol: String, // http
        @SerializedName("root_account_file")
        val rootAccountFile: String, // https://gist.githubusercontent.com/buckyroberts/........json
        @SerializedName("root_account_file_hash")
        val rootAccountFileHash: String, // hash
        @SerializedName("seed_block_identifier")
        val seedBlockIdentifier: String,
        val trust: String, // 100.00
        val version: String // v1.0
    )
}
