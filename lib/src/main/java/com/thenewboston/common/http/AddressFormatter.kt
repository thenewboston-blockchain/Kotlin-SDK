package com.thenewboston.common.http

import com.thenewboston.common.model.BankConfig

class AddressFormatter {
    fun formatFromBankConfig(bankConfig: BankConfig): String =
        "${bankConfig.protocol}://${bankConfig.ipAddress}:${bankConfig.port}"

}