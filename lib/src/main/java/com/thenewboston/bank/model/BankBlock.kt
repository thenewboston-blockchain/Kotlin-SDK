package com.thenewboston.bank.model

<<<<<<< HEAD
import kotlinx.datetime.LocalDateTime
=======
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97
import java.util.Date
import kotlinx.serialization.SerialName

data class BankBlock(
    val id: String,
    @SerialName("balance_key")
    val balanceKey: String,
    val sender: String,
    val signature: String,
<<<<<<< HEAD
    val created: LocalDateTime,
    val modified: LocalDateTime
)

=======
    val created: Date,
    val modified: Date
)
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97
