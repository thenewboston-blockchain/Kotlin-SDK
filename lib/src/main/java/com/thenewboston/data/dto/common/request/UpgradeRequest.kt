package com.thenewboston.data.dto.common.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpgradeRequest(
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("signature")
    val signature: String,
    @SerialName("message")
    val message: UpgradeRequestMessage
)

@Serializable
data class UpgradeRequestMessage(
    @SerialName("bank_node_identifier")
    val bankNodeIdentifier: String? = null,
    @SerialName("validator_node_identifier")
    val validatorNodeIdentifier: String? = null
)
