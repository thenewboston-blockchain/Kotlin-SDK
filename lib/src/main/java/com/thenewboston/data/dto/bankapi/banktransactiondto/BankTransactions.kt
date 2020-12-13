package com.thenewboston.data.dto.bankapi.banktransactiondto

import com.thenewboston.data.dto.bankapi.blockdto.Block
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
    val bankTransactions: List<BankTransactions>
)

@Serializable
data class BankTransactions(
    @SerialName("id")
    val id: String,
    @SerialName("block")
    val block: Block,
    @SerialName("amount")
    val amount: Double,
    @SerialName("recipient")
    val recipient: String
)
