package com.thenewboston.data.dto.bankapi.crawl.response

import com.thenewboston.utils.DateSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Crawl(
    @Serializable(with = DateSerializer::class)
    @SerialName("crawl_last_completed")
    val crawlLastCompleted: LocalDateTime,

    @SerialName("crawl_status")
    val crawlStatus: String,

    @SerialName("ip_address")
    val ipAddress: String,

    @SerialName("port")
    val port: Int,

    @SerialName("protocol")
    val protocol: String
)
