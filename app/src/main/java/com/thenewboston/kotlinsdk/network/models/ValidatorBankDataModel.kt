package com.thenewboston.kotlinsdk.network.models


import com.google.gson.annotations.SerializedName


data class ValidatorBankDataModel(
    @SerializedName("account_number")
    val accountNumber: String, // acc_no
    @SerializedName("confirmation_expiration")
    val confirmationExpiration: Int?,
    @SerializedName("default_transaction_fee")
    val defaultTransactionFee: Int, // 1
    @SerializedName("ip_address")
    val ipAddress: String, // 143.110.137.54
    @SerializedName("node_identifier")
    val nodeIdentifier: String, // nid
    val port: Int?, // 80
    val protocol: String, // http
    val trust: String, // 0.00
    val version: String // v1.0
)
