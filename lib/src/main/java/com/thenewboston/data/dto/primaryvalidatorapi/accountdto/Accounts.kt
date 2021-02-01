package com.thenewboston.data.dto.primaryvalidatorapi.accountdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountFromValidatorList(
    val count: Long,
    val next: String?,
    val previous: String?,
    val results: List<AccountFromValidator>
)

@Serializable
data class AccountFromValidator(
    @SerialName("id")
    val id: String,

    @SerialName("account_number")
    val accountNumber: String,

    @SerialName("balance")
    val balance: Int,

    @SerialName("balance_lock")
    val balanceLock: String
)

@Serializable
data class AccountBalance(
    @SerialName("balance")
    val balance: Int,
)

@Serializable
data class AccountBalanceLock(
    @SerialName("balance_lock")
    val balanceLock: String,
)
