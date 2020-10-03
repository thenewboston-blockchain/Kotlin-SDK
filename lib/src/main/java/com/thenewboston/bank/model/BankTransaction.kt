package com.thenewboston.bank.model

data class BankTransaction(
    val id: String,
    val block: String,
    val sender: String,
    val recipient: String,
    val amount: Number
)