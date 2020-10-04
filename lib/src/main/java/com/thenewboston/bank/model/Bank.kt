package com.thenewboston.bank.model

import com.thenewboston.common.model.BankNode
import com.thenewboston.common.model.NodeType
import com.thenewboston.common.model.ValidatorNode
import kotlinx.serialization.SerialName

/**
 * The [Bank] entity holds the business relevant information
 */
data class Bank(
    @SerialName("account_number")
    val accountNumber: String,
    @SerialName("ip_address")
    val ipAddress: String,
    val port: String,
    val protocol: String,
    val version: String,
    val txFee: Int,
    @SerialName("node_type")
    val nodeType: NodeType,
    val accounts: List<BankAccount>,
    val transactions: List<BankTransaction>,
    val blocks: List<BankBlock>,
    val confirmations: List<BlockInformation>,
    @SerialName("invalid_blocks")
    val invalidBlocks: List<BlockInformation>,
    val banks: List<BankNode>,
    val validators: List<ValidatorNode>,
    @SerialName("confirmation_services")
    val confirmationServices: List<BankConfirmationService>
)