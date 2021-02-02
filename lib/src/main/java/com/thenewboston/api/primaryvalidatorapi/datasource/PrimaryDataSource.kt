package com.thenewboston.api.primaryvalidatorapi.datasource

import com.thenewboston.api.common.GetDataSource
import com.thenewboston.api.common.PostDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.common.http.makeApiCall
import com.thenewboston.data.dto.common.response.Validator
import com.thenewboston.data.dto.common.response.ValidatorList
import com.thenewboston.utils.ErrorMessages
import com.thenewboston.utils.PaginationOptions
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class PrimaryDataSource @Inject constructor(
    private val getDataSource: GetDataSource,
    private val postDataSource: PostDataSource
) {

    suspend fun fetchBankFromValidator(nodeIdentifier: String) = makeApiCall(
        call = { getDataSource.bankFromValidator(nodeIdentifier) },
        errorMessage = "Failed to retrieve bank from validator"
    )

    suspend fun fetchBanksFromValidator(pagination: PaginationOptions) = makeApiCall(
        call = { getDataSource.banksFromValidator(pagination) },
        errorMessage = ErrorMessages.EMPTY_LIST_MESSAGE
    )

    suspend fun fetchPrimaryValidatorDetails() = makeApiCall(
        call = { getDataSource.primaryValidatorDetails() },
        errorMessage = "Failed to retrieve primary validator details"
    )

    suspend fun fetchValidators(pagination: PaginationOptions): Outcome<ValidatorList> = makeApiCall(
        call = { getDataSource.validators(pagination) },
        errorMessage = "Could not fetch list of validators"
    )

    suspend fun fetchValidator(nodeIdentifier: String): Outcome<Validator> = makeApiCall(
        call = { getDataSource.validator(nodeIdentifier) },
        errorMessage = "Could not fetch validator with NID $nodeIdentifier"
    )
}
