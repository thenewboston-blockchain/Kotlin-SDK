package com.thenewboston.account.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AccountTest {
    @Test
    fun accountShouldBeCreated() {
        val account = Account("1234", "12334455", "test", "testKey", BigDecimal.ONE, "1")

        Assertions.assertNotNull(account)
    }
}
