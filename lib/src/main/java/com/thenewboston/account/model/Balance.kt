package com.thenewboston.account.model

import java.math.BigDecimal

data class Balance (val balanceValue: BigDecimal){
    init {
        require(balanceValue > BigDecimal.ZERO){
            "Balance must not be negative"
        }
    }
}
