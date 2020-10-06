package com.thenewboston.data.dto.bankapi.banktransactiondto

data class BankTransactionDTO(
    val id: String,
    val block: BlockDTO,
    val amount: Double,
    val recipient: String
)