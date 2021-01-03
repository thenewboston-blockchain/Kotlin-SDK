package com.thenewboston.data.dto.bankapi.common.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Request")
data class PostRequest<T> (

    @SerialName("message")
    val message: T,

    @SerialName("node_identifier")
    val nodeIdentifier: String,

    @SerialName("signature")
    val signature: String
)

typealias PatchRequest<T> = PostRequest<T>
