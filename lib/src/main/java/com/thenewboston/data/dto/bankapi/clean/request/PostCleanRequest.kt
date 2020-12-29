package com.thenewboston.data.dto.bankapi.clean.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostCleanRequest(
    @SerialName("data")
    val data: Data,

    @SerialName("signature")
    val signature: String
)

@Serializable
data class Data(
    @SerialName("clean")
    val clean: String
)
