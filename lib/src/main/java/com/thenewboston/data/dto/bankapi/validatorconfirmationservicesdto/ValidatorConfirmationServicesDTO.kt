package com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ValidatorConfirmationServicesDTO(
    val id: String,
    @SerialName("created_date")
    val createdDate: String,

    @SerialName("modified_date")
    val modifiedDate: String?,

    @SerialName("end")
    val end: String,

    @SerialName("start")
    val start: String,

    @SerialName("validator")
    val validator: String
)
