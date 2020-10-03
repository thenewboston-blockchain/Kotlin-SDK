package com.thenewboston.account.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AccountTest {
    @Test
    fun accountShouldBeCreated() {
        val account = Account("1234", Balance(BigDecimal.valueOf(100L)), BalanceLock("1"))

        Assertions.assertNotNull(account)
    }

    @Test
    fun balanceShouldThrowExceptionOnEmptyAccountNumber() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Account(" ", Balance(BigDecimal.valueOf(100L)), BalanceLock("1"))
        }
    }
}
