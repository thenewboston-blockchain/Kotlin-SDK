package com.thenewboston.bank.model

import kotlinx.serialization.SerialName
import java.util.Date

data class BankAccount(
    val id: String,
    @SerialName("account_number")
    val accountNumber: String,
    val trust: Float,
    val created: Date,
    val modified: Date
)