package com.thenewboston.data.dto.bankapi.bankdto

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BankDTOTest {
    private val accountNumbers = listOf<String>(
        "5e12967707909e62b2bb2036c209085a784fabbc3deccefee70052b6181c8ed8",
        "db1a9ac3c356ab744ab4ad5256bb86c2f6dfaa7c1aece1f026a08dbd8c7178f2"
    )
    private val ipAddresses = listOf<String>("83.168.1.232", "74.124.1.68")
    private val nodeIdentifiers = listOf<String>(
        "d5356888dc9303e44ce52b1e06c3165a7759b9df1e6a6dfbd33ee1c3df1ab4d1",
        "3214108063cda7b259782c57ff8cec343ad2f1ad35baf38c3503db5cf6f3b2f7"
    )
    private val ports = listOf<Int>(80, 80)
    private val protocols = listOf<String>("http", "http")
    private val defaultTransactionFees = listOf<Double>(1.0000000000000000, 2.5000000000000000)
    private val trusts = listOf<Double>(100.00, 98.32)
    private val jsonStringArray =
        """
        [
  {
    "account_number": "5e12967707909e62b2bb2036c209085a784fabbc3deccefee70052b6181c8ed8",
    "ip_address": "83.168.1.232",
    "node_identifier": "d5356888dc9303e44ce52b1e06c3165a7759b9df1e6a6dfbd33ee1c3df1ab4d1",
    "port": 80,
    "protocol": "http",
    "version": "v1.0",
    "default_transaction_fee": "1.0000000000000000",
    "trust": "100.00"
  },
  {
    "account_number": "db1a9ac3c356ab744ab4ad5256bb86c2f6dfaa7c1aece1f026a08dbd8c7178f2",
    "ip_address": "74.124.1.68",
    "node_identifier": "3214108063cda7b259782c57ff8cec343ad2f1ad35baf38c3503db5cf6f3b2f7",
    "port": 80,
    "protocol": "http",
    "version": "v1.0",
    "default_transaction_fee": "2.5000000000000000",
    "trust": "98.32"
  }
]

"""

    @Test
    fun bankTest() {
        val banks: List<BankDTO> = Json.decodeFromString(jsonStringArray)

        banks.forEachIndexed { index, bankDTO ->
            Assertions.assertEquals(accountNumbers[index], bankDTO.accountNumber)
            Assertions.assertEquals(ipAddresses[index], bankDTO.ipAddress)
            Assertions.assertEquals(trusts[index], bankDTO.trust)
            Assertions.assertEquals(defaultTransactionFees[index], bankDTO.defaultTransactionFee)
            Assertions.assertEquals(nodeIdentifiers[index], bankDTO.nodeIdentifier)
            Assertions.assertEquals(ports[index], bankDTO.port)
        }
    }
}
