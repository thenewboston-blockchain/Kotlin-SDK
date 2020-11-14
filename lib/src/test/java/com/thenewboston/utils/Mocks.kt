package com.thenewboston.utils

import com.thenewboston.bank.model.Bank

object Mocks {

    fun banks(): Bank {
        val result = com.thenewboston.bank.model.Result(
            "1",
            "",
            "",
            "80",
            "http",
            "v1",
            1,
            100.00)
        val results = listOf(result)
        return Bank(count = 1, results = results)
    }
}