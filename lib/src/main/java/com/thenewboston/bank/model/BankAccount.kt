package com.thenewboston.bank.model

import java.util.Date

data class BankAccount(
    val id: String,
    val accountNumber: String,
    val trust: Float,
    val created: Date,
    val modified: Date
)