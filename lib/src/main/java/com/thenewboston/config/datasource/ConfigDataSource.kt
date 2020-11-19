package com.thenewboston.config.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.data.dto.bankapi.configdto.ConfigDTO
import io.ktor.client.request.*
import io.ktor.utils.io.errors.*

private const val CONFIG_ENDPOINT = "/config"

class ConfigDataSource(private val networkClient: NetworkClient) {
    suspend fun fetchConfig(): Outcome<ConfigDTO> = makeApiCall(
        call = { doFetchConfig() },
        errorMessage = "Could not fetch config"
    )

    private suspend fun doFetchConfig(): Outcome<ConfigDTO> {
        val urlSuffix = "$CONFIG_ENDPOINT"
        val config = networkClient.client.get<ConfigDTO>(urlSuffix)

        return when {
            config.accountNumber.isNullOrEmpty() -> Outcome.Error("Received null or empty account number in response", IOException())
            else -> Outcome.Success(config)
        }
    }
}
