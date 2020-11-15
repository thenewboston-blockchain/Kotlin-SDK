package com.thenewboston.utils

import com.thenewboston.bank.model.Bank
import com.thenewboston.bank.model.BankList
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorDTO
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorListDTO

object Mocks {

    fun banks() = BankList(
        count = 1,
        banks = listOf(bank(), bank())
    )

    fun bank() = Bank(
        "1",
        "",
        "",
        "80",
        "http",
        "v1",
        1,
        100.00
    )

    fun validators() = ValidatorListDTO(
        count = 1,
        previous = null,
        next = null,
        results = listOf(validator(), validator())
    )

    fun validator() = ValidatorDTO(
        accountNumber = "",
        ipAddress = "127.0.0.1",
        nodeIdentifier = "",
        port = 80,
        protocol = "http",
        version = "1",
        defaultTransactionFee = 0.0,
        rootAccountFile = "",
        rootAccountFileHash = "",
        seedBlockIdentifier = "",
        dailyConfirmationRate = null,
        trust = 100.0
    )
}
