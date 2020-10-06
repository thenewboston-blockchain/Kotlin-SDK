package com.thenewboston.validator.model.request

import java.math.BigDecimal
import kotlinx.serialization.SerialName

data class ValidatorRequest(
    val message: Message,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    val signature: String
)

data class Message(
    val trust: BigDecimal
)
