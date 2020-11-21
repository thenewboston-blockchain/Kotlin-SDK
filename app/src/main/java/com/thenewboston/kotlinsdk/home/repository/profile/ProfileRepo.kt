package com.thenewboston.kotlinsdk.home.repository.profile

import com.thenewboston.kotlinsdk.network.models.GenericListDataModel

interface ProfileRepo {
    suspend fun getAccountBalance(accountNumber: String): Int?
    suspend fun getAccountTransactions(accountNumber: String, limit: Int, offset: Int): Pair<String?, GenericListDataModel?>
}
