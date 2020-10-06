package com.thenewboston.bank.model

import java.util.Date
import kotlinx.serialization.SerialName

data class BankAccount(
    val id: String,
    @SerialName("account_number")
    val accountNumber: String,
    val trust: Float,
    val created: Date,
    val modified: Date
)

