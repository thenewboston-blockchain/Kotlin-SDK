package com.thenewboston.validator.model.request

import kotlinx.serialization.SerialName
import java.math.BigDecimal

data class ValidatorRequest(
    val message: Message,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    val signature: String
)

data class Message(
    val trust: BigDecimal
)