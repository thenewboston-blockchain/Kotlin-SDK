package com.thenewboston.data.dto.bankapi.validatorconfirmationservicesdto

import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValidatorConfirmationServicesDTOTest {
    private val ids = listOf<String>(
        "be9fbc3b-d4df-43d5-9bea-9882a6dd27f6",
        "e2055637-67ff-4479-aec6-a8095d513862"
    )
    private val validators = listOf<String>(
        "51461a75-dd8d-4133-81f4-543a3b054149",
        "10308b02-d577-484e-953c-0a2bdb5e3d83"
    )
<<<<<<< HEAD
    private val jsonStringArray = """
=======
    private val jsonStringArray =
        """
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97
        
        [
          {
            "id": "be9fbc3b-d4df-43d5-9bea-9882a6dd27f6",
            "created_date": "2020-07-09T22:10:35.312956Z",
            "modified_date": "2020-07-09T22:10:37.393578Z",
            "end": "2020-08-09T22:10:24Z",
            "start": "2020-07-09T22:10:25Z",
            "validator": "51461a75-dd8d-4133-81f4-543a3b054149"
          },
          {
            "id": "e2055637-67ff-4479-aec6-a8095d513862",
            "created_date": "2020-07-09T22:10:35.312956Z",
            "modified_date": "2020-07-09T22:10:37.393578Z",
            "end": "2020-08-09T22:10:24Z",
            "start": "2020-07-09T22:10:25Z",
            "validator": "10308b02-d577-484e-953c-0a2bdb5e3d83"
          }
        ]
<<<<<<< HEAD
    """.trimIndent()
=======
        """.trimIndent()
>>>>>>> 6b0b8d4d041131c88b65fd352d54d26096a20f97

    @Test
    fun validatorConfirmationServicesTest() {
        val services =
            Gson().fromJson(jsonStringArray, Array<ValidatorConfirmationServicesDTO>::class.java)
                .toList()

        services.forEachIndexed { index, validatorConfirmationServicesDTO ->
            assertEquals(ids[index], validatorConfirmationServicesDTO.id)
            assertEquals(validators[index], validatorConfirmationServicesDTO.validator)
        }
    }
}
