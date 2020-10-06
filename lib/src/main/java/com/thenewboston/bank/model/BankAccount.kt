package com.thenewboston.bank.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName

data class BankAccount(
    val id: String,
    @SerialName("account_number")
    val accountNumber: String,
    val trust: Float,
    val created: LocalDateTime,
    val modified: LocalDateTime
)

