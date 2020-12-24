package com.thenewboston.data.dto.bankapi.common.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias UpdateTrustRequest = PatchRequest<TrustMessage>

@Serializable
data class TrustMessage(
    @SerialName("trust")
    val trust: Double
)

@Serializable
@SerialName("Request")
data class PatchRequest<T> (
    @SerialName("message")
    val message: T,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("signature")
    val signature: String
)
