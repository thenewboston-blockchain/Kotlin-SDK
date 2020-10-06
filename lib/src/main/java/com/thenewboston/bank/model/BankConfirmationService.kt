package com.thenewboston.bank.model

import kotlinx.datetime.LocalDateTime

data class BankConfirmationService(
    val created: LocalDateTime,
    val end: LocalDateTime,
    val id: String,
    val modified: LocalDateTime,
    val start: LocalDateTime,
    val validator: String
)
