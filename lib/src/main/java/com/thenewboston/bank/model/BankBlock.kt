package com.thenewboston.bank.model

import kotlinx.datetime.LocalDateTime
import java.util.Date
import kotlinx.serialization.SerialName

data class BankBlock(
    val id: String,
    @SerialName("balance_key")
    val balanceKey: String,
    val sender: String,
    val signature: String,
    val created: LocalDateTime,
    val modified: LocalDateTime
)

