package com.thenewboston.common.http.bankapi.banktransactiondto


data class BankTransactionDTO(
    val id: String,
    val block: BlockDTO,
    val amount: Double,
    val recipient: String

)



