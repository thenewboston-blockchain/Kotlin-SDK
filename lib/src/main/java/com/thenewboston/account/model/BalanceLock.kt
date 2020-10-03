package com.thenewboston.account.model

import org.apache.commons.lang3.StringUtils

data class BalanceLock(val value: String) {
    init {
        require(StringUtils.isNotBlank(value)) {
            "balance lock must not be empty"
        }
    }
}
