package com.thenewboston.kotlinsdk.network.models


import com.google.gson.annotations.SerializedName

data class BankAccountsDataModel(
    @SerializedName("account_number")
    val accountNumber: String, // acc_no
    @SerializedName("created_date")
    val createdDate: String, // 2020-10-08T02:18:07.346849Z
    val id: String, // 5a8c7990-393a-4299-ae92-2f096a
    @SerializedName("modified_date")
    val modifiedDate: String, // 2020-10-08T02:18:07.346914Z
    val trust: String // 0.00
)
