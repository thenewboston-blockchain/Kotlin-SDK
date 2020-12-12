package com.thenewboston.data.dto.bankapi.bankdto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankTrustResponse(
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("ip_address")
    val ipAddress: String,
    @SerialName("port")
    val port: Int? = null,
    @SerialName("protocol")
    val protocol: String,
    @SerialName("default_transaction_fee")
    val defaultTransactionFee: Double,
    @SerialName("trust")
    val trust: Double
)
