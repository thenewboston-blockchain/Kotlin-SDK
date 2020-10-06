package com.thenewboston.data.dto.bankapi.confirmationblockdto

import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConfirmationBlockDTOTest {
<<<<<<< HEAD
    private val jsonString = """{
=======
    private val jsonString =
        """{
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97
    "id": 1,
    "created_date": "2020-05-28T23:41:54.749018Z",
    "modified_date": "2020-05-28T23:41:54.749040Z",
    "block_identifier": "65ae26192dfb9ec41f88c6d582b374a9b42ab58833e1612452d7a8f685dcd4d5",
    "block": 1,
    "validator": 1
<<<<<<< HEAD
  }""".trimIndent()
=======
  }
        """.trimIndent()
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97

    @Test
    fun confirmationBlockTest() {
        val confBlock = Gson().fromJson(jsonString, ConfirmationBlockDTO::class.java)

        confBlock.apply {
            assertEquals(1, id)

            assertEquals(
                "65ae26192dfb9ec41f88c6d582b374a9b42ab58833e1612452d7a8f685dcd4d5",
                blockIdentifier
            )
            assertEquals(1, block)
            assertEquals(1, validator)
        }
    }
}
