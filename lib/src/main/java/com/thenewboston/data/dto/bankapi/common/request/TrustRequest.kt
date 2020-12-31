package com.thenewboston.data.dto.bankapi.common.request

import com.thenewboston.data.dto.bankapi.common.request.PostRequest


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


typealias UpdateTrustRequest = @Serializable PostRequest<TrustMessage>

@Serializable
data class TrustMessage(

    @SerialName("trust")
    val trust: Double
)
