package com.thenewboston.api.primaryvalidatorapi.repository

import com.thenewboston.api.primaryvalidatorapi.datasource.PrimaryDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.common.response.ValidatorList
import com.thenewboston.data.dto.primaryvalidatorapi.configdto.PrimaryValidatorDetails
import com.thenewboston.utils.PaginationOptions
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class PrimaryRepository @Inject constructor(private val dataSource: PrimaryDataSource) {

    suspend fun primaryValidatorDetails(): Outcome<PrimaryValidatorDetails> =
        dataSource.fetchPrimaryValidatorDetails()

    suspend fun validators(offset: Int, limit: Int): Outcome<ValidatorList> =
        dataSource.fetchValidators(PaginationOptions(offset, limit))

    suspend fun validator(nodeIdentifier: String) = dataSource.fetchValidator(nodeIdentifier)
}
