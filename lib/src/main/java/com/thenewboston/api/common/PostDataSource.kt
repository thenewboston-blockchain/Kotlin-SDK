package com.thenewboston.api.common

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.clean.request.PostCleanRequest
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.data.dto.common.request.ConnectionRequest
import com.thenewboston.data.dto.bankapi.crawl.request.PostCrawlRequest
import com.thenewboston.data.dto.bankapi.crawl.response.Crawl
import com.thenewboston.data.dto.bankapi.upgradenoticedto.UpgradeNoticeRequest
import com.thenewboston.data.dto.common.response.ConfirmationServices
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request.PostConfirmationServicesRequest
import com.thenewboston.data.dto.common.response.ConfirmationBlockMessage
import com.thenewboston.data.dto.common.response.ConfirmationBlocks
import com.thenewboston.data.dto.common.response.ValidatorDetails
import com.thenewboston.data.dto.confirmationvalidatorapi.upgraderequestdto.UpgradeRequest
import com.thenewboston.data.dto.primaryvalidatorapi.bankblockdto.BankBlock
import com.thenewboston.data.dto.primaryvalidatorapi.bankblockdto.request.BankBlockRequest
import com.thenewboston.utils.BankAPIEndpoints
import com.thenewboston.utils.PrimaryValidatorAPIEndpoints
import com.thenewboston.utils.ConfirmationValidatorAPIEndpoints
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.errors.*
import javax.inject.Inject

class PostDataSource @Inject constructor(private val networkClient: NetworkClient) {

    suspend fun doSendBankBlock(request: BankBlockRequest): Outcome<BankBlock> {
        val response = networkClient.defaultClient.post<BankBlock> {
            url(PrimaryValidatorAPIEndpoints.BANK_BLOCKS_ENDPOINT)
            body = request
        }

        return Outcome.Success(response)
    }

    suspend fun doSendConfirmationServices(request: PostConfirmationServicesRequest): Outcome<ConfirmationServices> {
        val response = networkClient.defaultClient.post<ConfirmationServices> {
            url(BankAPIEndpoints.VALIDATOR_CONFIRMATION_SERVICES_ENDPOINT)
            body = request
        }

        return when {
            response.id.isBlank() -> {
                val nodeIdentifier = request.nodeIdentifier
                val message =
                    "Received invalid response sending confirmation services with node identifier: $nodeIdentifier"
                return Outcome.Error(message, IOException())
            }
            else -> Outcome.Success(response)
        }
    }

    suspend fun doSendConfirmationBlocks(request: ConfirmationBlocks): Outcome<ConfirmationBlockMessage> {
        val response = networkClient.defaultClient.post<ConfirmationBlockMessage> {
            url(ConfirmationValidatorAPIEndpoints.CONFIRMATION_BLOCKS_ENDPOINT)
            body = request
        }

        return Outcome.Success(response)
    }

    suspend fun doSendUpgradeNotice(request: UpgradeNoticeRequest): Outcome<String> {
        networkClient.defaultClient.post<HttpResponse> {
            url(BankAPIEndpoints.UPGRADE_NOTICE_ENDPOINT)
            body = request
        }

        // Return success as response body is empty
        return Outcome.Success("Successfully sent upgrade notice")
    }

    suspend fun doSendUpgradeRequest(request: UpgradeRequest): Outcome<ValidatorDetails> {
        val response = networkClient.defaultClient.post<ValidatorDetails> {
            url(ConfirmationValidatorAPIEndpoints.UPGRADE_REQUEST)
            body = request
        }

        return Outcome.Success(response)
    }

    suspend fun doSendClean(request: PostCleanRequest): Outcome<Clean> {
        val response = networkClient.defaultClient.post<Clean> {
            url(BankAPIEndpoints.CLEAN_ENDPOINT)
            body = request
        }

        return when {
            response.cleanStatus.isEmpty() -> {
                val clean = request.data.clean
                val message = "Received invalid response when sending block with clean: $clean"
                Outcome.Error(message, IOException())
            }
            else -> Outcome.Success(response)
        }
    }

    suspend fun doSendConnectionRequests(request: ConnectionRequest): Outcome<String> {
        networkClient.defaultClient.post<HttpResponse> {
            url(BankAPIEndpoints.CONNECTION_REQUESTS_ENDPOINT)
            body = request
        }

        return Outcome.Success("Successfully sent connection requests")
    }

    suspend fun doSendPrimaryValidatorUpdated(request: ConnectionRequest): Outcome<String> {
        networkClient.defaultClient.post<HttpResponse> {
            url(ConfirmationValidatorAPIEndpoints.PRIMARY_VALIDATOR_UPDATED)
            body = request
        }

        return Outcome.Success("Successfully updated primary validator")
    }

    suspend fun doSendCrawl(request: PostCrawlRequest): Outcome<Crawl> {
        val response = networkClient.defaultClient.post<Crawl> {
            url(BankAPIEndpoints.CRAWL_ENDPOINT)
            body = request
        }

        return when {
            response.crawlStatus.isEmpty() -> {
                val crawl = request.data.crawl
                val message = "Received invalid response when sending block with crawl: $crawl"
                Outcome.Error(message, IOException())
            }
            else -> Outcome.Success(response)
        }
    }
}
