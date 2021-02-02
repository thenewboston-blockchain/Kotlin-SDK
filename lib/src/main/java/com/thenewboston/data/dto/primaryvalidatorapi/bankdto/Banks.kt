package com.thenewboston.data.dto.primaryvalidatorapi.bankdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankFromValidatorList(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String? = null,
    @SerialName("previous")
    val previous: String? = null,
    @SerialName("results")
    val banks: List<BankFromValidator>
)

@Serializable
data class BankFromValidator(
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
    @SerialName("confirmation_expiration")
    val confirmation_expiration: Int? = null,
    @SerialName("trust")
    val trust: Double
)
