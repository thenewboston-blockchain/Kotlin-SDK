package com.thenewboston.account.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class BalanceTest {

    @Test
    fun balanceShouldBeCreated() {
        val balance = Balance(BigDecimal.valueOf(1L))

        Assertions.assertNotNull(balance)
    }

    @Test
    fun balanceShouldThrowExceptionOnEmptyAccountNumber() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            Balance(BigDecimal.valueOf(-1L))
        }
    }

}
