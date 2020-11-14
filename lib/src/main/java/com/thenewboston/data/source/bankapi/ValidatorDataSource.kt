package com.thenewboston.data.source.bankapi

import com.thenewboston.common.http.NetworkClient
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorDTO
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorListDTO
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI

private const val VALIDATORS_ENDPOINT = "/validators"

private const val GENERIC_ERROR_MESSAGE = "An error occurred"

@KtorExperimentalAPI
internal class ValidatorDataSource(private val networkClient: NetworkClient) {

    suspend fun fetchValidators(): Outcome<ValidatorListDTO> = try {
        val validators = networkClient.client.get<ValidatorListDTO>(VALIDATORS_ENDPOINT)

        when {
            validators.results.isNullOrEmpty() -> Outcome.Error("Could not fetch validators", null)
            else -> Outcome.Success(validators)
        }
    } catch (e: Exception) {
        Outcome.Error(GENERIC_ERROR_MESSAGE, e)
    }

    suspend fun fetchValidator(nodeIdentifier: String): Outcome<ValidatorDTO> = try {
        val urlSuffix = "$VALIDATORS_ENDPOINT/$nodeIdentifier"
        val validator = networkClient.client.get<ValidatorDTO>(urlSuffix)
        Outcome.Success(validator)
    } catch (e: Exception) {
        Outcome.Error(GENERIC_ERROR_MESSAGE, e)
    }
}
