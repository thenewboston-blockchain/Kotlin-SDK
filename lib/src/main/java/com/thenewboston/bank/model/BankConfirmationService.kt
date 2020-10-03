package com.thenewboston.bank.model

import java.util.Date

data class BankConfirmationService(
    val created: Date,
    val end: Date,
    val id: String,
    val modified: Date,
    val start: Date,
    val validator: String
)