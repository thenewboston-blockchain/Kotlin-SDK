package com.thenewboston.account.model

import org.apache.commons.lang3.StringUtils

data class AccountNumber(val accountNumber: String) {
    init {
        require(StringUtils.isNotBlank(accountNumber)) {
            "Account number must not be empty"
        }
    }
}
