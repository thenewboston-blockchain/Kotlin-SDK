package com.thenewboston.account.model

import java.math.BigDecimal

data class Account(
    val id: String,
    val accountNumber: String,
    val nickName: String,
    val signingKey: String,
    val balance: BigDecimal,
    val balanceLock: String
);
