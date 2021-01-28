package com.thenewboston.api.primaryvalidatorapi.repository

import com.thenewboston.api.primaryvalidatorapi.datasource.PrimaryDataSource
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.primaryvalidatorapi.configdto.PrimaryValidatorDetails
import com.thenewboston.data.dto.primaryvalidatorapi.bankdto.BankFromValidator
import com.thenewboston.data.dto.primaryvalidatorapi.bankdto.BankFromValidatorList
import com.thenewboston.utils.PaginationOptions
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class PrimaryRepository @Inject constructor(private val dataSource: PrimaryDataSource) {

    suspend fun bankFromValidator(nodeIdentifier: String): Outcome<BankFromValidator> =
        dataSource.fetchBankFromValidator(nodeIdentifier)

    suspend fun banksFromValidator(offset: Int, limit: Int): Outcome<BankFromValidatorList> =
        dataSource.fetchBanksFromValidator(PaginationOptions(offset, limit))

    suspend fun primaryValidatorDetails(): Outcome<PrimaryValidatorDetails> =
        dataSource.fetchPrimaryValidatorDetails()
}
