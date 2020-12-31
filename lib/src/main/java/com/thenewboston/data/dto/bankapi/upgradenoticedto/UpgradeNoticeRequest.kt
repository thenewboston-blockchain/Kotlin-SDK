package com.thenewboston.data.dto.bankapi.upgradenoticedto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpgradeNoticeRequest(
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("signature")
    val signature: String,
    @SerialName("message")
    val message: UpgradeNoticeMessage
)

@Serializable
data class UpgradeNoticeMessage(
    @SerialName("bank_node_identifier")
    val bankNodeIdentifier: String
)
