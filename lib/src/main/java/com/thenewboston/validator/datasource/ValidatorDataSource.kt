package com.thenewboston.validator.datasource

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorDTO
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorListDTO
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI

private const val VALIDATORS_ENDPOINT = "/validators"

@KtorExperimentalAPI
internal class ValidatorDataSource(private val networkClient: NetworkClient) {

    suspend fun fetchValidators(): Outcome<ValidatorListDTO> = makeApiCall(
        call = { doFetchValidators() },
        errorMessage = "Could not fetch list of validators"
    )

    private suspend fun doFetchValidators(): Outcome<ValidatorListDTO> {
        val validators = networkClient.defaultClient.get<ValidatorListDTO>(VALIDATORS_ENDPOINT)

        return when {
            validators.results.isNullOrEmpty() -> Outcome.Error("Received null or empty list", null)
            else -> Outcome.Success(validators)
        }
    }

    suspend fun fetchValidator(nodeIdentifier: String): Outcome<ValidatorDTO> = makeApiCall(
        call = { doFetchValidator(nodeIdentifier) },
        errorMessage = "Could not fetch validator with NID $nodeIdentifier"
    )

    private suspend fun doFetchValidator(nodeIdentifier: String): Outcome<ValidatorDTO> {
        val urlSuffix = "$VALIDATORS_ENDPOINT/$nodeIdentifier"
        val validator = networkClient.defaultClient.get<ValidatorDTO>(urlSuffix)

        return Outcome.Success(validator)
    }
}
