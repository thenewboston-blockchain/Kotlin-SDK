package com.thenewboston.common.model

import kotlinx.serialization.SerialName

abstract class Node(
    @SerialName("network_id")
    val networkId: String,
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("tx_fee")
    val txFee: Int,
    val protocol: String,
    @SerialName("ip_address")
    val ipAddress: String,
    val port: String,
    val trust: Float,
    val version: String
) {

}