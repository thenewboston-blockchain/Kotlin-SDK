package com.thenewboston.common.dto.bankapi.validatordto

import com.thenewboston.common.dto.ValidatorDTO
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ValidatorDTOTest {
    private val accountNumbers = listOf<String>(
        "ad1f8845c6a1abb6011a2a434a079a087c460657aad54329a84b406dce8bf314",
        "4d2ec91f37bc553bc538e91195669b666e26b2ea3e4e31507e38102a758d4f86"
    )
    private val ipAddresses = listOf<String>("192.168.1.74", "86.168.1.23")
    private val nodeIdentifiers = listOf<String>(
        "3afdf37573f1a511def0bd85553404b7091a76bcd79cdcebba1310527b167521",
        "59479a31c3b91d96bb7a0b3e07f18d4bf301f1bb0bde05f8d36d9611dcbe7cbf"
    )
    private val ports = listOf<Int>(8000, 80)
    private val protocol = "http"
    private val defaultTransactionFees = listOf<Double>(4.0000000000000000, 2.0000000000000000)
    private val version = "v1.0"
    private val seedBlockIdentifier = ""
    private val trusts = listOf<Double>(100.00, 91.56)
    private val dailyRate = 1.2000000000000000
    private val rootFiles = listOf<String>(
        "https://gist.githubusercontent.com/buckyroberts/" +
                "519b5cb82a0a5b5d4ae8a2175b722520/raw/9237deb449e27cab93cb89ea3346ecdfc61fe9ea/0.json",
        "https://gist.githubusercontent.com/buckyroberts/" +
                "519b5cb82a0a5b5d4ae8a2175b722520/raw/9237deb449e27cab93cb89ea3346ecdfc61fe9ea/0.json"
    )
    private val rootHashes = listOf<String>(
        "4694e1ee1dcfd8ee5f989e59ae40a9f751812bf5ca52aca2766b322c4060672b",
        "4694e1ee1dcfd8ee5f989e59ae40a9f751812bf5ca52aca2766b322c4060672b"
    )
    private val jsonStringArray =
        """
        [
          {
            "account_number": "ad1f8845c6a1abb6011a2a434a079a087c460657aad54329a84b406dce8bf314",
            "ip_address": "192.168.1.74",
            "node_identifier": "3afdf37573f1a511def0bd85553404b7091a76bcd79cdcebba1310527b167521",
            "port": 8000,
            "protocol": "http",
            "version": "v1.0",
            "default_transaction_fee": "4.0000000000000000",
            "root_account_file": "https://gist.githubusercontent.com/buckyroberts/519b5cb82a0a5b5d4ae8a2175b722520/raw/9237deb449e27cab93cb89ea3346ecdfc61fe9ea/0.json",
            "root_account_file_hash": "4694e1ee1dcfd8ee5f989e59ae40a9f751812bf5ca52aca2766b322c4060672b",
            "seed_block_identifier": "",
            "daily_confirmation_rate": null,
            "trust": "100.00"
          },
          {
            "account_number": "4d2ec91f37bc553bc538e91195669b666e26b2ea3e4e31507e38102a758d4f86",
            "ip_address": "86.168.1.23",
            "node_identifier": "59479a31c3b91d96bb7a0b3e07f18d4bf301f1bb0bde05f8d36d9611dcbe7cbf",
            "port": 80,
            "protocol": "http",
            "version": "v1.0",
            "default_transaction_fee": "2.0000000000000000",
            "root_account_file": "https://gist.githubusercontent.com/buckyroberts/519b5cb82a0a5b5d4ae8a2175b722520/raw/9237deb449e27cab93cb89ea3346ecdfc61fe9ea/0.json",
            "root_account_file_hash": "4694e1ee1dcfd8ee5f989e59ae40a9f751812bf5ca52aca2766b322c4060672b",
            "seed_block_identifier": "",
            "daily_confirmation_rate": "1.2000000000000000",
            "trust": "91.56"
          }
        ]
        """.trimIndent()

    @Test
    fun validatorTest() {
        val validator: List<ValidatorDTO> = Json.decodeFromString(jsonStringArray)

        validator.forEachIndexed { index, validatorDTO ->
            assertEquals(accountNumbers[index], validatorDTO.accountNumber)
            assertEquals(ipAddresses[index], validatorDTO.ipAddress)
            assertEquals(ports[index], validatorDTO.port)
            assertEquals(version, validatorDTO.version)
            assertEquals(protocol, validatorDTO.protocol)
            assertEquals(defaultTransactionFees[index], validatorDTO.defaultTransactionFee)
            assertEquals(seedBlockIdentifier, validatorDTO.seedBlockIdentifier)
            assertEquals(trusts[index], validatorDTO.trust)
            assertEquals(rootFiles[index], validatorDTO.rootAccountFile)
            assertEquals(rootHashes[index], validatorDTO.rootAccountFileHash)
            if (index == 0) assertNull(validatorDTO.dailyConfirmationRate) else assertEquals(
                dailyRate,
                validatorDTO.dailyConfirmationRate
            )
        }
    }
}
