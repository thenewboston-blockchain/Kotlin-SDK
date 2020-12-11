package com.thenewboston.utils

import com.thenewboston.data.dto.bankapi.accountdto.AccountDTO
import com.thenewboston.data.dto.bankapi.accountdto.AccountList
import com.thenewboston.data.dto.bankapi.bankdto.request.BankTrustRequest
import com.thenewboston.data.dto.bankapi.bankdto.request.Message
import com.thenewboston.data.dto.bankapi.bankdto.response.Bank
import com.thenewboston.data.dto.bankapi.bankdto.response.BankList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankTrustResponse
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransaction
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.banktransactiondto.Block
import com.thenewboston.data.dto.bankapi.banktransactiondto.BlockList
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
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

    fun internalServerError() = BankAPIError(500, "Internal Server Error")

    fun bankTrustRequest(): BankTrustRequest {
        val signature =
            "93952df29ae3885fd9c9f88721314236bdb53ca5632b2959dcf5cf3c38cb8b96ca57ff84c5337eb164f803237f901abcb0c41a9f71e14aa2fb3159c7ad7a7509"
        val nodeIdentifier = "35f4c988f425809ca7f5d0b319cdf8f7d7aba1b064fd0efc85d61fa0f4d05145"
        return BankTrustRequest(
            Message(10.0),
            nodeIdentifier,
            signature
        )
    }

    fun bankTrustResponse() = BankTrustResponse(
        "dfddf07ec15cbf363ecb52eedd7133b70b3ec896b488460bcecaba63e8e36be5",
        "127.0.0.1",
        80,
        "http",
        1.0,
        10.0
    )

    fun emptyBankTrustResponse() = BankTrustResponse(
        "",
        "",
        0,
        "",
        0.0,
        0.0
    )
}
