package com.thenewboston.bank.model

<<<<<<< HEAD
import kotlinx.datetime.LocalDateTime

data class BankConfirmationService(
    val created: LocalDateTime,
    val end: LocalDateTime,
    val id: String,
    val modified: LocalDateTime,
    val start: LocalDateTime,
=======
import java.util.Date

data class BankConfirmationService(
    val created: Date,
    val end: Date,
    val id: String,
    val modified: Date,
    val start: Date,
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97
    val validator: String
)
