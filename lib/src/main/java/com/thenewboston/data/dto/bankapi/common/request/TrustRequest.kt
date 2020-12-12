package com.thenewboston.data.dto.bankapi.common.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTrustRequest(

    @SerialName("message")
    val message: TrustMessage,

    @SerialName("node_identifier")
    val nodeIdentifier: String,

    @SerialName("signature")
    val signature: String
)

@Serializable
data class TrustMessage(

    @SerialName("trust")
    val trust: Double
)
