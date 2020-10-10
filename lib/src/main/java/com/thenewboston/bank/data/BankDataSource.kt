package com.thenewboston.bank.data

import com.thenewboston.common.http.HttpService
import com.thenewboston.common.model.BankConfig
import io.ktor.util.*

@KtorExperimentalAPI
internal class BankDataSource(
    val httpService: HttpService
) {

    suspend fun connect(config: BankConfig) {
        httpService.doGet(config.ipAddress)
    }
}