package com.thenewboston.bank.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankDTO(
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
    val defaultTransactionFee: Double,

    @SerialName("trust")
    val trust: Double
)
