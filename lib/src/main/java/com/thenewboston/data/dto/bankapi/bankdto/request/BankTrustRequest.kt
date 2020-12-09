package com.thenewboston.data.dto.bankapi.bankdto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankTrustRequest(
    @SerialName("message")
    val message: Message,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("signature")
    val signature: String
)

@Serializable
data class Message(
    @SerialName("trust")
    val trust: Double
)
