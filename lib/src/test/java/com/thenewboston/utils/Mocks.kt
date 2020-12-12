package com.thenewboston.utils

import com.thenewboston.data.dto.bankapi.accountdto.response.Account
import com.thenewboston.data.dto.bankapi.accountdto.response.AccountList
import com.thenewboston.data.dto.bankapi.bankdto.response.Bank
import com.thenewboston.data.dto.bankapi.bankdto.response.BankList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankTrustResponse
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransaction
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.banktransactiondto.Block
import com.thenewboston.data.dto.bankapi.banktransactiondto.BlockList
import com.thenewboston.data.dto.bankapi.common.request.TrustMessage
import com.thenewboston.data.dto.bankapi.common.request.UpdateTrustRequest
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

    fun account(trust: Double = 100.0) = Account(
        id = Some.id,
        createdDate = LocalDateTime(2020, 8, 8, 12, 12, 23),
        modifiedDate = LocalDateTime(2020, 8, 8, 12, 13, 23),
        accountNumber = Some.accountNumber,
        trust = trust
    )

    fun emptyAccount() = Account(
        id = "",
        createdDate = LocalDateTime(2020, 8, 8, 12, 12, 23),
        modifiedDate = null,
        accountNumber = "",
        trust = 0.00
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

    fun trustRequest(trust: Double = Some.trust): UpdateTrustRequest {
        val signature =
            "93952df29ae3885fd9c9f88721314236bdb53ca5632b2959dcf5cf3c38cb8b96ca57ff84c5337eb164f803237f901abcb0c41a9f71e14aa2fb3159c7ad7a7509"
        val nodeIdentifier = "35f4c988f425809ca7f5d0b319cdf8f7d7aba1b064fd0efc85d61fa0f4d05145"
        return UpdateTrustRequest(
            TrustMessage(trust),
            nodeIdentifier,
            signature
        )
    }

    fun bankTrustResponse(trust: Double = Some.trust) = BankTrustResponse(
        "dfddf07ec15cbf363ecb52eedd7133b70b3ec896b488460bcecaba63e8e36be5",
        "127.0.0.1",
        80,
        "http",
        1.0,
        trust
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

// Sample values taken from docs, see https://thenewboston.com/bank-api/
object Some {
    const val id = "64426fc5-b3ac-42fb-b75b-d5ccfcdc6872"
    const val accountNumber = "0cdd4ba04456ca169baca3d66eace869520c62fe84421329086e03d91a68acdb"
    const val nodeIdentifier = "d5356888dc9303e44ce52b1e06c3165a7759b9df1e6a6dfbd33ee1c3df1ab4d1"
    const val signature = "f41788fe19690a67abe3336d4ca84565c090691efae0e5cdd8bf02e126842215080405013b8461f734d091e673e9edefca53a51773fda59bbebcef77ab8e2901"
    const val trust = 42.0
}
