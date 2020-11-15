package com.thenewboston.validator.repository

import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.bankapi.validatordto.ValidatorListDTO
import com.thenewboston.validator.datasource.ValidatorDataSource
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
internal class ValidatorRepository(private val dataSource: ValidatorDataSource) {

    suspend fun validators(): Outcome<ValidatorListDTO> = dataSource.fetchValidators()

    suspend fun validator(nodeIdentifier: String) = dataSource.fetchValidator(nodeIdentifier)
}
