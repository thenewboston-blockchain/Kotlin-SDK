package com.thenewboston.utils

import com.thenewboston.data.dto.bankapi.accountdto.response.Account
import com.thenewboston.data.dto.bankapi.accountdto.response.AccountList
import com.thenewboston.data.dto.bankapi.bankdto.response.BankList
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactionList
import com.thenewboston.data.dto.bankapi.banktransactiondto.BankTransactions
import com.thenewboston.data.dto.bankapi.blockdto.Block
import com.thenewboston.data.dto.bankapi.blockdto.BlockList
import com.thenewboston.data.dto.bankapi.blockdto.request.BlockMessage
import com.thenewboston.data.dto.bankapi.blockdto.request.PostBlockRequest
import com.thenewboston.data.dto.bankapi.clean.request.Data as cleanData
import com.thenewboston.data.dto.bankapi.clean.request.PostCleanRequest
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.data.dto.bankapi.configdto.BankDetails
import com.thenewboston.data.dto.common.request.ConnectionRequest
import com.thenewboston.data.dto.common.request.ConnectionRequestMessage
import com.thenewboston.data.dto.bankapi.crawl.request.Data as crawlData
import com.thenewboston.data.dto.bankapi.crawl.request.PostCrawlRequest
import com.thenewboston.data.dto.bankapi.crawl.response.Crawl
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlock
import com.thenewboston.data.dto.bankapi.invalidblockdto.InvalidBlockList
import com.thenewboston.data.dto.bankapi.invalidblockdto.request.*
import com.thenewboston.data.dto.bankapi.upgradenoticedto.UpgradeNoticeMessage
import com.thenewboston.data.dto.bankapi.upgradenoticedto.UpgradeNoticeRequest
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.ConfirmationServices
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.ConfirmationServicesList
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request.Message
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request.PostConfirmationServicesRequest
import com.thenewboston.data.dto.common.request.TrustMessage
import com.thenewboston.data.dto.common.request.UpdateTrustRequest
import com.thenewboston.data.dto.common.response.*
import com.thenewboston.data.dto.primaryvalidatorapi.accountdto.AccountBalance
import com.thenewboston.data.dto.primaryvalidatorapi.accountdto.AccountBalanceLock
import com.thenewboston.data.dto.primaryvalidatorapi.accountdto.AccountFromValidator
import com.thenewboston.data.dto.primaryvalidatorapi.accountdto.AccountFromValidatorList
import com.thenewboston.data.dto.primaryvalidatorapi.bankdto.BankFromValidator
import com.thenewboston.data.dto.primaryvalidatorapi.bankdto.BankFromValidatorList
import com.thenewboston.data.dto.primaryvalidatorapi.configdto.PrimaryValidatorDetails
import com.thenewboston.utils.mocks.BankAPIError
import com.thenewboston.utils.mocks.PaginationResult
import kotlinx.datetime.LocalDateTime

object Mocks {

    fun banks(pagination: PaginationOptions = PaginationOptions(0, 20)) = BankList(
        count = 30,
        banks = PaginationResult<Bank>(pagination.limit, bank()).toList()
    )

    fun emptyBanks() = BankList(
        count = 30,
        banks = emptyList()
    )

    fun bank(trust: Double = Some.trust) = Bank(
        "dfddf07ec15cbf363ecb52eedd7133b70b3ec896b488460bcecaba63e8e36be5",
        "127.0.0.1",
        "",
        80,
        "http",
        "v1",
        1,
        trust
    )

    fun emptyBank() = Bank(
        "",
        "",
        "",
        null,
        "",
        "",
        0,
        0.0
    )

    fun banksFromValidator(pagination: PaginationOptions = PaginationOptions(0, 20)) = BankFromValidatorList(
        count = 30,
        banks = PaginationResult<BankFromValidator>(pagination.limit, bankFromValidator()).toList()
    )

    fun emptyBanksFromValidator() = BankFromValidatorList(
        count = 30,
        banks = emptyList()
    )

    fun bankFromValidator() = BankFromValidator(
        "dfddf07ec15cbf363ecb52eedd7133b70b3ec896b488460bcecaba63e8e36be5",
        "127.0.0.1",
        Some.nodeIdentifier,
        80,
        "http",
        "v1",
        1,
        null,
        Some.trust
    )

    fun emptyBankFromValidator() = BankFromValidator(
        "",
        "",
        "",
        null,
        "",
        "",
        0,
        null,
        0.0
    )

    fun bankDetails() = BankDetails(
        validator(),
        "1",
        "",
        "",
        80,
        "http",
        "v1",
        1
    )

    fun emptyBankDetails() = BankDetails(
        emptyValidator(),
        "",
        "",
        "",
        null,
        "",
        "",
        0
    )

    fun primaryValidatorDetails() = PrimaryValidatorDetails(
        validator(),
        Some.accountNumber,
        Some.ipAddress,
        Some.nodeIdentifier,
        80,
        "http",
        "v1",
        1,
        "http://20.188.33.93/media/root_account_file.json",
        "b2885f94cd099a8c5ba5355ff9cdd69252b4cad2541e32d20152702397722cf5",
        "",
        100,
        "PRIMARY_VALIDATOR"
    )

    fun emptyPrimaryValidatorDetails() = PrimaryValidatorDetails(
        emptyValidator(),
        "",
        "",
        "",
        null,
        "",
        "",
        0,
        "",
        "",
        "",
        0,
        ""
    )

    fun validators(pagination: PaginationOptions = PaginationOptions(0, 20)) = ValidatorList(
        count = 30,
        previous = null,
        next = null,
        results = PaginationResult<Validator>(pagination.limit, validator()).toList()
    )

    fun emptyValidators() = ValidatorList(
        count = 0,
        previous = null,
        next = null,
        results = emptyList()
    )

    fun validator() = Validator(
        accountNumber = "1",
        ipAddress = "127.0.0.1",
        nodeIdentifier = "6871913581c3e689c9f39853a77e7263a96fd38596e9139f40a367e28364da53",
        port = 80,
        protocol = "http",
        version = "1",
        defaultTransactionFee = 0,
        rootAccountFile = "",
        rootAccountFileHash = "",
        seedBlockIdentifier = "",
        dailyConfirmationRate = null,
        trust = 100.0
    )

    fun emptyValidator() = Validator(
        accountNumber = "",
        ipAddress = "",
        nodeIdentifier = "",
        port = 0,
        protocol = "",
        version = "",
        defaultTransactionFee = 0,
        rootAccountFile = "",
        rootAccountFileHash = "",
        seedBlockIdentifier = "",
        dailyConfirmationRate = null,
        trust = 0.0
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

    fun blocks(pagination: PaginationOptions = PaginationOptions(0, 20)) = BlockList(
        count = 30,
        previous = null,
        next = null,
        blocks = PaginationResult<Block>(pagination.limit, block()).toList()
    )

    fun emptyBlocks() = BlockList(
        count = 0,
        next = null,
        previous = null,
        blocks = emptyList()
    )

    fun bankTransaction() = BankTransactions(
        id = "String",
        block = block(),
        amount = 5.0,
        recipient = "String"
    )

    fun bankTransactions(pagination: PaginationOptions = PaginationOptions(0, 20)) = BankTransactionList(
        count = 30,
        next = null,
        previous = null,
        PaginationResult<BankTransactions>(pagination.limit, bankTransaction()).toList()
    )

    fun emptyBankTransactions() = BankTransactionList(
        count = 0,
        next = null,
        previous = null,
        bankTransactions = emptyList()
    )

    fun accounts(pagination: PaginationOptions = PaginationOptions(0, 20)) = AccountList(
        count = 30,
        previous = null,
        next = null,
        results = PaginationResult<Account>(pagination.limit, account()).toList()
    )

    fun emptyAccounts() = AccountList(
        count = 0,
        next = null,
        previous = null,
        results = emptyList()
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

    fun accountsFromValidator(pagination: PaginationOptions = PaginationOptions(0, 20)) = AccountFromValidatorList(
        count = 30,
        previous = null,
        next = null,
        results = PaginationResult<AccountFromValidator>(pagination.limit, accountFromValidator()).toList()
    )

    fun emptyAccountsFromValidator() = AccountList(
        count = 0,
        next = null,
        previous = null,
        results = emptyList()
    )

    fun accountFromValidator() = AccountFromValidator(
        id = Some.id,
        accountNumber = Some.accountNumber,
        balance = Some.balance,
        balanceLock = Some.balanceLock
    )

    fun emptyAccountFromValidator() = AccountFromValidator(
        id = "",
        accountNumber = "",
        balance = 0,
        balanceLock = ""
    )

    fun accountBalance() = AccountBalance(
        balance = Some.balance
    )

    fun accountBalanceLock() = AccountBalanceLock(
        balanceLock = Some.balanceLock
    )

    fun emptyAccountBalance() = AccountBalance(
        balance = 0
    )

    fun emptyAccountBalanceLock() = AccountBalanceLock(
        balanceLock = ""
    )

    fun invalidBlocks(pagination: PaginationOptions = PaginationOptions(0, 20)) = InvalidBlockList(
        count = 30,
        previous = null,
        next = null,
        results = PaginationResult<InvalidBlock>(pagination.limit, invalidBlock()).toList()
    )

    fun emptyInvalidBlocks() = InvalidBlockList(
        count = 0,
        previous = null,
        next = null,
        results = emptyList()
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
        val signature = "93952df29ae3885fd9c9f88721314236bdb53ca5632b2959dcf5cf3c38cb8b96ca57ff84c5337eb164f803237f901abcb0c41a9f71e14aa2fb3159c7ad7a7509"
        val nodeIdentifier = "35f4c988f425809ca7f5d0b319cdf8f7d7aba1b064fd0efc85d61fa0f4d05145"
        return UpdateTrustRequest(
            TrustMessage(trust),
            nodeIdentifier,
            signature
        )
    }

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

    fun confirmationServicesList(pagination: PaginationOptions = PaginationOptions(0, 20)) = ConfirmationServicesList(
        30,
        null,
        null,
        PaginationResult<ConfirmationServices>(pagination.limit, confirmationServices()).toList()
    )

    fun emptyConfirmationServicesList() = ConfirmationServicesList(
        0,
        null,
        null,
        emptyList()
    )

    private fun confirmationServices() = ConfirmationServices(
        Some.id,
        Some.dateTime.toString(),
        Some.dateTime.toString(),
        Some.endDate,
        Some.startDate,
        Some.signature
    )

    fun emptyConfirmationServices() = ConfirmationServices(
        "",
        "",
        "",
        "",
        "",
        ""
    )

    fun confirmationServiceWithMessage(message: Message) = ConfirmationServices(
        Some.id,
        Some.dateTime.toString(),
        Some.dateTime.toString(),
        message.end.toString(),
        message.start.toString(),
        Some.signature
    )

    fun confirmationServiceRequest() = PostConfirmationServicesRequest(
        Message(
            Some.dateTime,
            Some.dateTime
        ),
        Some.nodeIdentifier,
        Some.signature
    )

    fun upgradeNoticeRequest() = UpgradeNoticeRequest(
        Some.nodeIdentifier,
        Some.signature,
        UpgradeNoticeMessage(
            Some.bankNodeIdentifier
        )
    )

    fun cleanSuccess() = Clean(
        Some.dateTime,
        "cleaning",
        "20.188.56.203",
        80,
        "http",
    )

    fun cleanFailure() = Clean(
        Some.dateTime,
        "",
        "",
        80,
        "",
    )

    fun crawlSuccess() = Crawl(
        Some.dateTime,
        "crawling",
        "20.188.56.203",
        80,
        "http",
    )

    fun crawlFailure() = Crawl(
        Some.dateTime,
        "",
        "20.188.56.203",
        80,
        "",
    )

    fun postClean(clean: String = "cleaning") = Clean(
        cleanLastCompleted = Some.dateTime,
        cleanStatus = clean,
        ipAddress = "20.188.56.203",
        port = 80,
        protocol = "http"
    )

    fun postCleanRequest() = PostCleanRequest(
        data = cleanData(clean = "start"),
        signature = Some.signature
    )

    fun connectionRequest() = ConnectionRequest(
        ConnectionRequestMessage(
            Some.ipAddress,
            Some.port,
            Some.protocol
        ),
        Some.nodeIdentifier,
        Some.signature
    )

    fun postCrawlRequest() = PostCrawlRequest(
        data = crawlData(crawl = "start"),
        signature = Some.signature
    )

    fun postCrawl(crawl: String = "crawling") = Crawl(
        crawlLastCompleted = Some.dateTime,
        crawlStatus = crawl,
        ipAddress = "20.188.56.203",
        port = 80,
        protocol = "http"
    )

    fun confirmationBlocks(): ConfirmationBlocks {
        return ConfirmationBlocks(
            ConfirmationBlockMessage(
                ConfirmationBlock(
                    Some.accountNumber,
                    MessageBalance(
                        Some.balanceKey,
                        listOf(Tx(100, Some.recipient))
                    ),
                    Some.signature
                ),
                Some.blockIdentifier,
                listOf(
                    UpdatedBalance(
                        Some.accountNumber,
                        10,
                        Some.balanceLock
                    )
                )
            ),
            Some.nodeIdentifier,
            Some.signature
        )
    }

    fun paginationOptionsDefault(): PaginationOptions = PaginationOptions(0, 20)

    fun paginationOptionsTwenty(): PaginationOptions = PaginationOptions(20, 20)

    fun paginationOptionsThirty(): PaginationOptions = PaginationOptions(0, 30)
}

// Sample values taken from docs, see https://thenewboston.com/bank-api/
object Some {
    const val id = "64426fc5-b3ac-42fb-b75b-d5ccfcdc6872"
    const val accountNumber = "0cdd4ba04456ca169baca3d66eace869520c62fe84421329086e03d91a68acdb"
    const val nodeIdentifier = "d5356888dc9303e44ce52b1e06c3165a7759b9df1e6a6dfbd33ee1c3df1ab4d1"
    const val signature =
        "f41788fe19690a67abe3336d4ca84565c090691efae0e5cdd8bf02e126842215080405013b8461f734d091e673e9edefca53a51773fda59bbebcef77ab8e2901"
    const val trust = 42.0
    const val balanceKey = "ce51f0d9facaa7d3e69657429dd3f961ce70077a8efb53dcda508c7c0a19d2e3"
    val dateTime = LocalDateTime.parse("2020-11-19T19:57:31.799872")
    const val startDate = "2020-11-29T07:54:16.233806Z"
    const val endDate = "2020-12-15T07:54:16.233806Z"
    const val bankNodeIdentifier = "d5356888dc9303e44ce52b1e06c3165a7759b9df1e6a6dfbd33ee1c3df1ab4d1"
    const val ipAddress = "172.19.0.13"
    const val port = 8080
    const val protocol = "http"
    const val balance = 403
    const val balanceLock = "aca94f4d2f472c6b9b662f60aab247b9c6aef2079d63b870e2cc02308a7c822b"
    const val recipient = "fdae688d9e879ce89f164c6eb793d5a3c9e714bc6962a671275c0e2e1e6ea599"
    const val blockIdentifier = "d5356888dc9303e44ce52b1e06c3165a7759b9df1e6a6dfbd33ee1c3df1ab4d1"
}
