package com.thenewboston.account.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BalanceLockTest {
    @Test
    fun balanceLockShouldBeCreated() {
        val balanceLock = BalanceLock("1")

        Assertions.assertNotNull(balanceLock)
    }

    @Test
    fun balanceShouldThrowExceptionOnEmptyAccountNumber() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            BalanceLock(" ")
        }
    }
}
