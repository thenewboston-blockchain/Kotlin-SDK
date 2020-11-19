package com.thenewboston.bank.model

import java.util.Date
import kotlinx.serialization.SerialName

data class BankBlock(
    val id: String,
    @SerialName("balance_key")
    val balanceKey: String,
    val sender: String,
    val signature: String,
    val created: Date,
    val modified: Date
)
