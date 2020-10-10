package com.thenewboston.bank.data.dto

data class BankTransactionDTO(
    val id: String,
    val block: BlockDTO,
    val amount: Double,
    val recipient: String
)
