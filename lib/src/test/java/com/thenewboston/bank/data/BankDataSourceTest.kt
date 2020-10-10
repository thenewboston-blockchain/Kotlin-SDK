package com.thenewboston.bank.data

import com.thenewboston.common.http.AddressFormatter
import com.thenewboston.common.http.HttpService
import com.thenewboston.common.http.ResponseMapper
import com.thenewboston.common.model.BankConfig
import io.ktor.util.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
@KtorExperimentalAPI
class BankDataSourceTest {
    private val httpService: HttpService = mockk()
    private val addressFormatter: AddressFormatter = mockk()
    private val responseMapper: ResponseMapper = mockk()

    private val subject = BankDataSource(
        httpService = httpService,
        addressFormatter = addressFormatter,
        responseMapper = responseMapper
    )

    @Test
    fun `should throw an IllegalStateException when connect is called and node is not a bank`() {
        runBlockingTest {
            val config: BankConfig = mockk()
            val expectedUrl = "https://blabla.com"
            val jsonResult = "{}"
            every { addressFormatter.formatFromBankConfig(bankConfig = config) } returns expectedUrl
            coEvery { httpService.doGet("$expectedUrl/config") } returns mockk()
            subject.connect(config)

            coVerify {
                httpService.doGet("$expectedUrl/config")
            }
        }
    }
}