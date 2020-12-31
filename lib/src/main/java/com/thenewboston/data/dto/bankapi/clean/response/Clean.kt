package com.thenewboston.data.dto.bankapi.clean.response

import com.thenewboston.utils.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Clean(
    @Serializable(with = DateSerializer::class)
    @SerialName("clean_last_completed")
    val cleanLastCompleted: LocalDateTime,

    @SerialName("clean_status")
    val cleanStatus: String,

    @SerialName("ip_address")
    val ipAddress: String,

    @SerialName("port")
    val port: Int,

    @SerialName("protocol")
    val protocol: String
)
