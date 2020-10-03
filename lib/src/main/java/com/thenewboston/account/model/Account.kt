package com.thenewboston.account.model

import org.apache.commons.lang3.StringUtils

data class Account(val id: String, val balance: Balance, val balanceLock: BalanceLock) {
    init {
        require(StringUtils.isNotBlank(id)) {
            "id must not be empty"
        }
    }
}
