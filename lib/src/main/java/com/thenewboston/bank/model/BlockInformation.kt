package com.thenewboston.bank.model

data class BlockInformation(
    val id: String,
    val block: String,
    val blockIdentifier: String,
    val validator: String
)