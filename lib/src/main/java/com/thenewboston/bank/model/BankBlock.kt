package com.thenewboston.bank.model

//import java.util.Date
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankBlock(
    @SerialName("id")
    val id: String,
    @SerialName("balance_key")
    val balanceKey: String,
    @SerialName("sender")
    val sender: String,
    @SerialName("signature")
    val signature: String,
    @SerialName("created_date")
    val created: String,
    @SerialName("modified_date")
    val modified: String
)
