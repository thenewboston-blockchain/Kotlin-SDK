package com.thenewboston.utils

import com.thenewboston.data.dto.bankapi.accountdto.response.Account
import com.thenewboston.data.dto.bankapi.accountdto.response.AccountList
import com.thenewboston.data.dto.bankapi.bankdto.response.Bank
import com.thenewboston.data.dto.bankapi.bankdto.response.BankList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankTrustResponse
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactions
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.blockdto.Block
import com.thenewboston.data.dto.bankapi.blockdto.BlockList
import com.thenewboston.data.dto.bankapi.blockdto.request.BlockMessage
import com.thenewboston.data.dto.bankapi.blockdto.request.PostBlockRequest
import com.thenewboston.data.dto.bankapi.common.request.TrustMessage
import com.thenewboston.data.dto.bankapi.common.request.UpdateTrustRequest
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlock
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlockList
import com.thenewboston.data.dto.bankapi.invalidblockdto.request.*
import com.thenewboston.data.dto.bankapi.validatordto.Validator
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorList
import kotlinx.datetime.LocalDateTime

object Mocks {

    fun banks() = BankList(
        count = 2,
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
        id = "3ff4ebb0-2b3d-429b-ba90-08133fcdee4e",
        createdDate = LocalDateTime.parse("2020-11-19T19:57:31.799872"),
        modifiedDate = LocalDateTime.parse("2020-11-19T19:57:31.799872"),
        balanceKey = "ce51f0d9facaa7d3e69657429dd3f961ce70077a8efb53dcda508c7c0a19d2e3",
        sender = "0cdd4ba04456ca169baca3d66eace869520c62fe84421329086e03d91a68acdb",
        signature = "ee5a2f2a2f5261c1b633e08dd61182fd0db5604c853ebd8498f6f28ce8e2ccbbc38093918610ea88a7ad47c7f3192ed955d9d1529e7e390013e43f25a5915c0f"
    )

    fun postBlock(balanceKey: String = "ce51f0d9facaa7d3e69657429dd3f961ce70077a8efb53dcda508c7c0a19d2e3") = Block(
        id = "3ff4ebb0-2b3d-429b-ba90-08133fcdee4e",
        createdDate = LocalDateTime.parse("2020-11-19T19:57:31.799872"),
        modifiedDate = LocalDateTime.parse("2020-11-19T19:57:31.799872"),
        balanceKey = balanceKey,
        sender = "0cdd4ba04456ca169baca3d66eace869520c62fe84421329086e03d91a68acdb",
        signature = "ee5a2f2a2f5261c1b633e08dd61182fd0db5604c853ebd8498f6f28ce8e2ccbbc38093918610ea88a7ad47c7f3192ed955d9d1529e7e390013e43f25a5915c0f"
    )

    fun emptyBlock() = Block(
        id = "",
        createdDate = Some.dateTime,
        modifiedDate = null,
        balanceKey = "",
        sender = "",
        signature = ""
    )

    fun blocks() = BlockList(
        count = 1,
        previous = null,
        next = null,
        blocks = listOf(block(), block())
    )

    fun bankTransaction() = BankTransactions(
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

    fun invalidBlocks() = InvalidBlockList(
        count = 2,
        previous = null,
        next = null,
        results = listOf(invalidBlock(), invalidBlock())
    )

    fun invalidBlock(blockIdentifier: String = Some.id) = InvalidBlock(
        id = "2bcd53c5-19f9-4226-ab04-3dfb17c3a1fe",
        createdDate = LocalDateTime.parse("2020-11-19T19:57:31.799872"),
        modifiedDate = LocalDateTime.parse("2020-11-19T19:57:31.799872"),
        blockIdentifier = blockIdentifier,
        block = "3ff4ebb0-2b3d-429b-ba90-08133fcdee4e",
        confirmationValidator = "fcd2dce8-9e4f-4bf1-8dac-cdbaf64e5ce8",
        primaryValidator = "51461a75-dd8d-4133-81f4-543a3b054149"
    )

    fun emptyInvalidBlock() = InvalidBlock(
        id = "",
        createdDate = Some.dateTime,
        modifiedDate = null,
        blockIdentifier = "",
        block = "",
        confirmationValidator = "",
        primaryValidator = ""
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

    fun postInvalidBlockRequest() = PostInvalidBlockRequest(
        message = InvalidBlockMessage(
            block = InvalidBlockMessageBlock(
                accountNumber = Some.accountNumber,
                message = InvalidBlockInnerMessage(
                    balanceKey = Some.balanceKey,
                    transactions = listOf(Transaction(1234.5, recipient = Some.accountNumber))
                ),
                signature = Some.signature
            ),
            blockIdentifier = Some.id,
            primaryValidatorNodeIdentifier = Some.nodeIdentifier
        ),
        nodeIdentifier = Some.nodeIdentifier,
        signature = Some.signature
    )

    fun postBlockRequest() = PostBlockRequest(
        accountNumber = Some.accountNumber,
        message = BlockMessage(
            balanceKey = Some.balanceKey,
            transactions = listOf(Transaction(12.5, "484b3176c63d5f37d808404af1a12c4b9649cd6f6769f35bdf5a816133623fbc"))
        ),
        signature = Some.signature
    )
}

// Sample values taken from docs, see https://thenewboston.com/bank-api/
object Some {
    const val id = "64426fc5-b3ac-42fb-b75b-d5ccfcdc6872"
    const val accountNumber = "0cdd4ba04456ca169baca3d66eace869520c62fe84421329086e03d91a68acdb"
    const val nodeIdentifier = "d5356888dc9303e44ce52b1e06c3165a7759b9df1e6a6dfbd33ee1c3df1ab4d1"
    const val signature = "f41788fe19690a67abe3336d4ca84565c090691efae0e5cdd8bf02e126842215080405013b8461f734d091e673e9edefca53a51773fda59bbebcef77ab8e2901"
    const val trust = 42.0
    const val balanceKey = "ce51f0d9facaa7d3e69657429dd3f961ce70077a8efb53dcda508c7c0a19d2e3"
    val dateTime = LocalDateTime.parse("2020-11-19T19:57:31.799872")
}
