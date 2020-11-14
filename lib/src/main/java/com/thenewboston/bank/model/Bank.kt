package com.thenewboston.bank.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The [Bank] entity holds the business relevant information
 */
@Serializable
data class Bank(
    @SerialName("count")
    val count : Int,
    @SerialName("next")
    val next : String? = null,
    @SerialName("previous")
    val previous : String? = null,
    @SerialName("results")
    val results : List<Result>
)
