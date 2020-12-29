package com.thenewboston.data.dto.bankapi.connectionrequestsdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectionRequest(
    @SerialName("message")
    val connectionRequestMessage: ConnectionRequestMessage,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("signature")
    val signature: String
)

@Serializable
data class ConnectionRequestMessage(
    @SerialName("ip_address")
    val ipAddress: String,
    @SerialName("port")
    val port: Int,
    @SerialName("protocol")
    val protocol: String
)
