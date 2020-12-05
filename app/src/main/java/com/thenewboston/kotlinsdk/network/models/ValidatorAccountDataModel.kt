package com.thenewboston.kotlinsdk.network.models


import com.google.gson.annotations.SerializedName

data class ValidatorAccountDataModel(
    @SerializedName("account_number")
    val accountNumber: String, // acc_no
    val balance: Long, // 6
    @SerializedName("balance_lock")
    val balanceLock: String, // bal_lock
    val id: String // 4cb2gv42be-eb2bf-43c8-9f86-826aaa2aff43d
) {}
