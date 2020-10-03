package com.thenewboston.bank.model

import java.util.Date

data class BankBlock(
    val id: String,
    val balanceKey: String,
    val sender: String,
    val signature: String,
    val created: Date,
    val modified: Date
)