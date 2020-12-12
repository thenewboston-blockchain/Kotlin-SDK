package com.thenewboston.data.dto.bankapi.bankdto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The [BankList] entity holds the business relevant information
 */
@Serializable
data class BankList(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String? = null,
    @SerialName("previous")
    val previous: String? = null,
    @SerialName("results")
    val banks: List<Bank>
)

@Serializable
data class Bank(
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("ip_address")
    val ipAddress: String,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("port")
    val port: String? = null,
    @SerialName("protocol")
    val protocol: String,
    @SerialName("version")
    val version: String,
    @SerialName("default_transaction_fee")
    val defaultTransactionFee: Int,
    @SerialName("trust")
    val trust: Double
)
