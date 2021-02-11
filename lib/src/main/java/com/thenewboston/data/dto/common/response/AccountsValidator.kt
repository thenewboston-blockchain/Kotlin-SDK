package com.thenewboston.data.dto.common.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountListValidator(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<AccountValidator>
)

@Serializable
data class AccountValidator(
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
