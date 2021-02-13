package com.thenewboston.data.dto.common.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidatorDetails(
    @SerialName("primary_validator")
    val primaryValidator: Validator,
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("ip_address")
    val ipAddress: String,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("port")
    val port: Int? = null,
    @SerialName("protocol")
    val protocol: String,
    @SerialName("version")
    val version: String,
    @SerialName("default_transaction_fee")
    val defaultTransactionFee: Int,
    @SerialName("root_account_file")
    val rootAccountFile: String,
    @SerialName("root_account_file_hash")
    val rootAccountFileHash: String,
    @SerialName("seed_block_identifier")
    val seedBlockIdentifier: String,
    @SerialName("daily_confirmation_rate")
    val dailyConfirmationRate: Int,
    @SerialName("node_type")
    val nodeType: String,
)
