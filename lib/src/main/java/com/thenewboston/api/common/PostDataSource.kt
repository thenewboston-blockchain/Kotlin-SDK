package com.thenewboston.api.common

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.clean.request.PostCleanRequest
import com.thenewboston.data.dto.bankapi.clean.response.Clean
import com.thenewboston.data.dto.common.request.ConnectionRequest
import com.thenewboston.data.dto.bankapi.crawl.request.PostCrawlRequest
import com.thenewboston.data.dto.bankapi.crawl.response.Crawl
import com.thenewboston.data.dto.bankapi.upgradenoticedto.UpgradeNoticeRequest
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.ConfirmationServices
import com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto.request.PostConfirmationServicesRequest
import com.thenewboston.utils.BankAPIEndpoints
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.errors.*
import javax.inject.Inject

class PostDataSource @Inject constructor(private val networkClient: NetworkClient) {

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

    suspend fun doSendUpgradeNotice(request: UpgradeNoticeRequest): Outcome<String> {
        networkClient.defaultClient.post<HttpResponse> {
            url(BankAPIEndpoints.UPGRADE_NOTICE_ENDPOINT)
            body = request
        }

        // Return success as response body is empty
        return Outcome.Success("Successfully sent upgrade notice")
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
