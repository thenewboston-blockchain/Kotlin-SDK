package com.thenewboston.validator.model.response

import java.math.BigDecimal
import kotlinx.serialization.SerialName

data class Validator(
    @SerialName("account_number")
    val accountNumber: String,

    @SerialName("ip_address")
    val ipAddress: String,

    @SerialName("node_identifier")
    val nodeIdentifier: String,

    @SerialName("port")
    val port: Int,

    @SerialName("protocol")
    val protocol: String,

    @SerialName("version")
    val version: String,

    @SerialName("default_transaction_fee")
    val defaultTransactionFee: BigDecimal,

    @SerialName("root_account_file")
    val rootAccountFile: String,

    @SerialName("root_account_file_hash")
    val rootAccountFileHash: String,

    @SerialName("seed_block_identifier")
    val seedBlockIdentifier: String,

    @SerialName("daily_confirmation_rate")
    val dailyConfirmationRate: BigDecimal,

    @SerialName("trust")
    val trust: BigDecimal
)
