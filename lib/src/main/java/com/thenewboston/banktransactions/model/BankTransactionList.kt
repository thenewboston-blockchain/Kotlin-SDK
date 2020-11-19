package com.thenewboston.banktransactions.model

import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The [BankTransactionList] entity holds the business relevant information
 */
@Serializable
data class BankTransactionList(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String? = null,
    @SerialName("previous")
    val previous: String? = null,
    @SerialName("results")
    val bankTransactions: List<BankTransactionDTO>
)
