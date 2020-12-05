package com.thenewboston.kotlinsdk.network.models


import com.google.gson.annotations.SerializedName

data class ProfileTransactionDataModel(
    val amount: Long, // 19600
    val block: Block,
    val id: String, // e711b06e-29ea-40f0-96db-d9as78
    val recipient: String // rec_id
    ) {
    data class Block(
        @SerializedName("balance_key")
        val balanceKey: String, // bal_key
        @SerializedName("created_date")
        val createdDate: String, // 2020-11-09T04:34:22.088477Z
        val id: String, // d12jka12s-da-sd
        @SerializedName("modified_date")
        val modifiedDate: String, // 2020-11-09T04:34:22.088524Z
        val sender: String, // sender_id
        val signature: String // sign
    )
}
