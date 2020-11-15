package com.thenewboston.utils

import com.thenewboston.bank.model.BankList

object Mocks {

    fun banks(): BankList {
        val result = com.thenewboston.bank.model.Bank(
            "1",
            "",
            "",
            "80",
            "http",
            "v1",
            1,
            100.00
        )

        val results = listOf(result)

        return BankList(
            count = 1,
            banks = results
        )
    }
}
