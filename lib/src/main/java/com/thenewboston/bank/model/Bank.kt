package com.thenewboston.bank.model

import com.thenewboston.common.model.BankNode
import com.thenewboston.common.model.NodeType
import com.thenewboston.common.model.ValidatorNode

data class Bank(
    val accountNumber: String,
    val ipAddress: String,
    val port: String,
    val protocol: String,
    val version: String,
    val txFee: Int,
    val nodeType: NodeType,
    val accounts: List<BankAccount>,
    val transactions: List<BankTransaction>,
    val blocks: List<BankBlock>,
    val confirmations: List<BlockInformation>,
    val invalidBlocks: List<BlockInformation>,
    val banks: List<BankNode>,
    val validators: List<ValidatorNode>,
    val confirmationServices: List<BankConfirmationService>
)