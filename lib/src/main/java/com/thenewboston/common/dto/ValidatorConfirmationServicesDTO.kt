package com.thenewboston.common.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ValidatorConfirmationServicesDTO(
    val id: String,
    @SerializedName("created_date")
    val createdDate: Date,
    @SerializedName("modified_date")
    val modifiedDate: Date?,
    val end: Date,
    val start: Date,
    val validator: String
)
