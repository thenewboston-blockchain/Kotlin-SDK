package com.thenewboston.account.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class AccountNumberTest {

    @Test
    fun accountNumberShouldBeCreated() {
        val accountNumber = AccountNumber("1234567890")
        Assertions.assertNotNull(accountNumber)
    }

    @Test
    fun accountNumberShouldThrowExceptionOnEmptyAccountNumber() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            AccountNumber("")
        }
    }
}
