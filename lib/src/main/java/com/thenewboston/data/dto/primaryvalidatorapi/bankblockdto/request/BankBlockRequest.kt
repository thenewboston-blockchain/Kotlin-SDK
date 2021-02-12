package com.thenewboston.data.dto.primaryvalidatorapi.bankblockdto.request

import com.thenewboston.data.dto.primaryvalidatorapi.bankblockdto.BankBlock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankBlockRequest(
    @SerialName("block")
    val block: BankBlock,
    @SerialName("node_identifier")
    val nodeIdentifier: String,
    @SerialName("signature")
    val signature: String
)
