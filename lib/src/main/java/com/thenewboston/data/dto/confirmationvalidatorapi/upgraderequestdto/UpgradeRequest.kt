package com.thenewboston.data.dto.confirmationvalidatorapi.upgraderequestdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpgradeRequestMessage(
    @SerialName("validator_node_identifier")
    val validatorNodeIdentifier: String
)

@Serializable
data class UpgradeRequest(
    @SerialName("message")
    val message: UpgradeRequestMessage,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("signature")
    val signature: String,
)
