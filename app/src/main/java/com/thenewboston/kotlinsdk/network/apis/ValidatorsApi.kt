package com.thenewboston.kotlinsdk.network.apis

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ValidatorsApi {

    @GET("/config")
    suspend fun getConfig(): Response<JsonElement>

    @GET("/accounts/{account_number}/balance")
    suspend fun getAccountBalance(
        @Path("account_number") accountNumber: String
    ): Response<JsonElement>

    @GET("/accounts")
    suspend fun getAccounts(
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
}
