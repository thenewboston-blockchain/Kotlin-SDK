package com.thenewboston.data.dto.bankapi.common.request

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.thenewboston.data.dto.bankapi.invalidblockdto.request.InvalidBlockMessage


@Serializable
data class PostOrPatchRequest(


    val message: InvalidBlockMessage,

    @SerialName("node_identifier")
    val nodeIdentifier: String,

    @SerialName("signature")
    val signature: String
)
