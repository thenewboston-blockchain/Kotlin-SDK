package com.thenewboston.data.dto.confirmationvalidatorapi.bankconfirmationservicesdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankConfirmationServicesList(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String? = null,
    @SerialName("previous")
    val previous: String? = null,
    @SerialName("results")
    val services: List<BankConfirmationServices>
)

@Serializable
data class BankConfirmationServices(
    @SerialName("id")
    val id: String,
    @SerialName("created_date")
    val createdDate: String,
    @SerialName("modified_date")
    val modifiedDate: String,
    @SerialName("end")
    val end: String,
    @SerialName("start")
    val start: String,
    @SerialName("validator")
    val bank: String
)
