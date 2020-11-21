package com.thenewboston.kotlinsdk.network.apis

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BankApis {

    @GET("/config")
    suspend fun getConfig(): Response<JsonElement>

    @GET("/validator_confirmation_services")
    suspend fun getValidatorConfirmationServices(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<JsonElement>

    @GET("/accounts")
    suspend fun getAccounts(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<JsonElement>

    @GET("/bank_transactions")
    suspend fun getBankTransactions(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<JsonElement>

    @GET("/bank_transactions")
    suspend fun getBlocks(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<JsonElement>

    @GET("/confirmation_blocks")
    suspend fun getConfirmationBlocks(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<JsonElement>

    @GET("/confirmation_blocks")
    suspend fun getInvalidBlocks(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<JsonElement>

    @GET("/banks")
    suspend fun getBanks(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<JsonElement>

    @GET("/validators")
    suspend fun getValidators(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<JsonElement>

    @GET("/bank_transactions")
    suspend fun getAccountTransactions(
        @Query("account_number") accountNumber: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<JsonElement>
}
