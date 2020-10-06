package com.thenewboston.bank.model

import kotlinx.serialization.SerialName

data class BlockInformation(
    val id: String,
    val block: String,
    @SerialName("block_identifier")
    val blockIdentifier: String,
    val validator: String
)
