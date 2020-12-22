package com.thenewboston.data.dto.bankapi.configdto

import com.thenewboston.data.dto.bankapi.validatordto.Validator
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankDetails(
    @SerialName("primary_validator")
    val primary_validator: Validator,
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
    val defaultTransactionFee: Int
)
