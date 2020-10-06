package com.thenewboston.bank.model

<<<<<<< HEAD
import kotlinx.datetime.LocalDateTime
=======
import java.util.Date
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97
import kotlinx.serialization.SerialName

data class BankAccount(
    val id: String,
    @SerialName("account_number")
    val accountNumber: String,
    val trust: Float,
<<<<<<< HEAD
    val created: LocalDateTime,
    val modified: LocalDateTime
)

=======
    val created: Date,
    val modified: Date
)
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97
