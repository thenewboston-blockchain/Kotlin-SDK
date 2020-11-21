package com.thenewboston.bank.model

import com.thenewboston.data.dto.bankapi.validatordto.ValidatorDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankDetails(
    @SerialName("primary_validator")
    val primary_validator: ValidatorDTO,
    @SerialName("account_number")
    val account_number: String,
    @SerialName("ip_address")
    val ip_address: String,
    @SerialName("node_identifier")
    val node_identifier: String,
    @SerialName("port")
    val port: String? = null,
    @SerialName("protocol")
    val protocol: String,
    @SerialName("version")
    val version: String,
    @SerialName("default_transaction_fee")
    val default_transaction_fee: Int
)
