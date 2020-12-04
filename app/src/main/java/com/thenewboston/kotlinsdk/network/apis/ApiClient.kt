package com.thenewboston.kotlinsdk.network.apis

import com.google.gson.GsonBuilder
import com.thenewboston.kotlinsdk.BuildConfig
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BANK_URL = "http://143.110.137.54/"
    private lateinit var retrofitForBank: Retrofit
    private lateinit var retrofitForValidators: Retrofit
    private val client by lazy {
        OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS).build() }

    val defaultBankClient: Retrofit get() {
        if (!this::retrofitForBank.isInitialized) {
            retrofitForBank = initializeClient(BANK_URL)
        }
        return retrofitForBank
    }

    fun validatorsClient(primaryValidator: String): Retrofit {
        if (!this::retrofitForValidators.isInitialized) {
            retrofitForValidators = initializeClient("http://$primaryValidator")
        }
        return retrofitForValidators
    }

    private fun initializeClient(endpoint: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(endpoint)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .build()
    }
}
