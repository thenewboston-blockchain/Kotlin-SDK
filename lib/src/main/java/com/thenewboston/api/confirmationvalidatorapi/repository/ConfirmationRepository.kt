package com.thenewboston.api.confirmationvalidatorapi.repository

import com.thenewboston.api.confirmationvalidatorapi.datasource.ConfirmationDataSource
import com.thenewboston.data.dto.common.response.ValidatorDetails
import com.thenewboston.common.http.Outcome
import com.thenewboston.data.dto.common.response.AccountListValidator
import com.thenewboston.data.dto.primaryvalidatorapi.bankdto.BankFromValidator
import com.thenewboston.data.dto.primaryvalidatorapi.bankdto.BankFromValidatorList
import com.thenewboston.utils.PaginationOptions
import javax.inject.Inject

class ConfirmationRepository @Inject constructor(private val dataSource: ConfirmationDataSource) {

    suspend fun accounts(offset: Int, limit: Int): Outcome<AccountListValidator> =
        dataSource.fetchAccounts(PaginationOptions(offset, limit))

    suspend fun validatorDetails(): Outcome<ValidatorDetails> =
        dataSource.fetchValidatorDetails()

    suspend fun bankFromValidator(nodeIdentifier: String): Outcome<BankFromValidator> =
        dataSource.fetchBankFromValidator(nodeIdentifier)

    suspend fun banksFromValidator(offset: Int, limit: Int): Outcome<BankFromValidatorList> =
        dataSource.fetchBanksFromValidator(PaginationOptions(offset, limit))
}
