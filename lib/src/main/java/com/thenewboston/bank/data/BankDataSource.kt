package com.thenewboston.bank.data

import com.thenewboston.common.dto.ConfigDTO
import com.thenewboston.common.http.AddressFormatter
import com.thenewboston.common.http.HttpService
import com.thenewboston.common.http.ResponseMapper
import com.thenewboston.common.model.BankConfig
import com.thenewboston.common.model.NodeType
import io.ktor.client.statement.*
import io.ktor.util.*

@KtorExperimentalAPI
internal class BankDataSource(
    val httpService: HttpService,
    private val addressFormatter: AddressFormatter,
    private val responseMapper: ResponseMapper
) {

    suspend fun connect(config: BankConfig) {
        val response: HttpResponse =
            httpService.doGet("${addressFormatter.formatFromBankConfig(config)}$CONFIG_ENDPOINT")
        val result = responseMapper.toObject<ConfigDTO>(response.readText())

        if (result.nodeType != NodeType.BANK.value) {
            throw IllegalStateException("Connected Node is not a Bank")
        }
    }


    companion object {
        private const val CONFIG_ENDPOINT = "/config"
    }
}