package com.thenewboston.common.dto.bankapi.accountdto

import com.thenewboston.common.dto.AccountDTO
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AccountDTOTest {
    private val ids = listOf<String>(
        "9eca00a5-d925-454c-a8d6-ecbb26ec2f76",
        "ae4d43b0-5c34-4e56-8266-0e3531268815"
    )
    private val accountNumbers = listOf<String>(
        "4d2ec91f37bc553bc538e91195669b666e26b2ea3e4e31507e38102a758d4f86",
        "a29baa6ba36f6db707f8f8dacfa82d5e8a28fa616e8cc96cf6d7790f551d79f2"
    )
    private val trusts = listOf<Double>(75.21, 94.63)
    private val jsonStringArray =
        """
            [
  {
    "id": "9eca00a5-d925-454c-a8d6-ecbb26ec2f76",
    "created_date": "2020-07-08T02:14:59.307535Z",
    "modified_date": "2020-07-08T02:14:59.307553Z",
    "account_number": "4d2ec91f37bc553bc538e91195669b666e26b2ea3e4e31507e38102a758d4f86",
    "trust": "75.21"
  },
  {
    "id": "ae4d43b0-5c34-4e56-8266-0e3531268815",
    "created_date": "2020-07-08T02:15:12.271834Z",
    "modified_date": "2020-07-08T02:15:12.271852Z",
    "account_number": "a29baa6ba36f6db707f8f8dacfa82d5e8a28fa616e8cc96cf6d7790f551d79f2",
    "trust": "94.63"
  }
]
        """.trimIndent()

    @Test
    fun accountsTest() {
        val accounts: List<AccountDTO> = Json.decodeFromString(jsonStringArray)

        accounts.forEachIndexed { index, accountDTO ->
            assertEquals(ids[index], accountDTO.id)
            assertEquals(accountNumbers[index], accountDTO.accountNumber)
            assertEquals(trusts[index], accountDTO.trust)
        }
    }
}
