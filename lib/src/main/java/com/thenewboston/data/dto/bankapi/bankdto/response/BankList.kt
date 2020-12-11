package com.thenewboston.data.dto.bankapi.bankdto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The [BankList] entity holds the business relevant information
 */
@Serializable
data class BankList(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String? = null,
    @SerialName("previous")
    val previous: String? = null,
    @SerialName("results")
    val banks: List<Bank>
)
