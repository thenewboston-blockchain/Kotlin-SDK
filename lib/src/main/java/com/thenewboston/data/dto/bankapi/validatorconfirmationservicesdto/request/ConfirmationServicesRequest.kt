package com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request

import com.thenewboston.utils.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostConfirmationServicesRequest(
    @SerialName("message")
    val message: Message,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("signature")
    val signature: String
)

@Serializable
data class Message(
    @Serializable(with = DateSerializer::class)
    @SerialName("end")
    val end: LocalDateTime,
    @Serializable(with = DateSerializer::class)
    @SerialName("start")
    val start: LocalDateTime

)
