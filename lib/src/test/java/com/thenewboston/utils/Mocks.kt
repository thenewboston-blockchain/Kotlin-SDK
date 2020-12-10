package com.thenewboston.utils

import com.thenewboston.data.dto.bankapi.accountdto.AccountDTO
import com.thenewboston.data.dto.bankapi.accountdto.AccountList
import com.thenewboston.data.dto.bankapi.bankdto.Bank
import com.thenewboston.data.dto.bankapi.bankdto.BankList
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransaction
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.banktransactiondto.Block
import com.thenewboston.data.dto.bankapi.banktransactiondto.BlockList
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlockDTO
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlockList
import com.thenewboston.data.dto.bankapi.validatordto.Validator
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorList
import kotlinx.datetime.LocalDateTime

object Mocks {

    fun banks() = BankList(
        count = 1,
        banks = listOf(bank(), bank())
    )

    fun bank() = Bank(
        "1",
        "",
        "",
        "80",
        "http",
        "v1",
        1,
        100.00
    )

    fun bankDetails() = BankDetails(
        validator(),
        "1",
        "",
        "",
        "80",
        "http",
        "v1",
        1
    )

    fun validators() = ValidatorList(
        count = 1,
        previous = null,
        next = null,
        results = listOf(validator(), validator())
    )

    fun validator() = Validator(
        accountNumber = "1",
        ipAddress = "127.0.0.1",
        nodeIdentifier = "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53",
        port = 80,
        protocol = "http",
        version = "1",
        defaultTransactionFee = 0.0,
        rootAccountFile = "",
        rootAccountFileHash = "",
        seedBlockIdentifier = "",
        dailyConfirmationRate = null,
        trust = 100.0
    )

    fun block() = Block(
        id = "String",
        createdDate = LocalDateTime.parse("2020-11-19T19:57:31.799872"),
        modifiedDate = LocalDateTime.parse("2020-11-19T19:57:31.799872"),
        balanceKey = "String",
        sender = "String",
        signature = "String"
    )

    fun blocks() = BlockList(
        count = 1,
        previous = null,
        next = null,
        results = listOf(block(), block())
    )

    fun bankTransaction() = BankTransaction(
        id = "String",
        block = block(),
        amount = 5.0,
        recipient = "String"
    )

    fun bankTransactions() = BankTransactionList(
        count = 1,
        next = null,
        previous = null,
        bankTransactions = listOf(bankTransaction(), bankTransaction())
    )

    fun accounts() = AccountList(
        count = 1,
        previous = null,
        next = null,
        results = listOf(account(), account())
    )

    fun account() = AccountDTO(
        id = "",
        createdDate = LocalDateTime(2020, 8, 8, 12, 12, 23),
        modifiedDate = LocalDateTime(2020, 8, 8, 12, 13, 23),
        accountNumber = "",
        trust = 100.0
    )

    fun invalidBlock() = InvalidBlockDTO(
        id = "2bcd53c5-19f9-4226-ab04-3dfb17c3a1fe",
        createdDate = LocalDateTime.parse("2020-11-19T19:57:31.799872"),
        modifiedDate = LocalDateTime.parse("2020-11-19T19:57:31.799872"),
        blockIdentifier = "65ae26192dfb9ec41f88c6d582b374a9b42ab58833e1612452d7a8f685dcd4d5",
        block = "3ff4ebb0-2b3d-429b-ba90-08133fcdee4e",
        confirmationValidator = "fcd2dce8-9e4f-4bf1-8dac-cdbaf64e5ce8",
        primaryValidator = "51461a75-dd8d-4133-81f4-543a3b054149"
    )

    fun invalidBlocks() = InvalidBlockList(
        count = 1,
        previous = null,
        next = null,
        results = listOf(invalidBlock(), invalidBlock())
    )

    fun internalServerError() = BankAPIError(500, "Internal Server Error")
}
