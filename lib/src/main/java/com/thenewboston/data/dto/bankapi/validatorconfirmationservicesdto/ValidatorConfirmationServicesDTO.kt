package com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto

import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime
import java.util.Date


data class ValidatorConfirmationServicesDTO(
    val id: String,
    @SerializedName("created_date")
    val createdDate: LocalDateTime,
    @SerializedName("modified_date")
    val modifiedDate: LocalDateTime?,
    val end: LocalDateTime,
    val start: Date,
    val validator: String
)
