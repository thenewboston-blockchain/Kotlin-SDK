package com.thenewboston.bank.model

import java.util.*

data class BankAccount(
    val id: String,
    val accountNumber: String,
    val trust: Float,
    val created: Date,
    val modified: Date
)