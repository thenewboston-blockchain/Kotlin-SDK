package com.thenewboston.data.dto.bankapi.crawl.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostCrawlRequest(
    @SerialName("data")
    val data: Data,

    @SerialName("signature")
    val signature: String
)

@Serializable
data class Data(
    @SerialName("crawl")
    val crawl: String
)
