package com.thenewboston.common.http

import io.ktor.util.*

interface HttpService {
    suspend fun <T> doGet(url: String): Pair<out T?, Error>
    suspend fun <T> doPost(url: String): Pair<T?, Error>
    suspend fun <T> doDelete(url: String): Pair<T?, Error>
    suspend fun <T> doPut(url: String): Pair<T?, Error>
}

@KtorExperimentalAPI
internal class HttpServiceImpl(private val networkClient: NetworkClient) : HttpService {

    override suspend fun <T> doGet(url: String): Pair<T?, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun <T> doPost(url: String): Pair<T?, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun <T> doDelete(url: String): Pair<T?, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun <T> doPut(url: String): Pair<T?, Error> {
        TODO("Not yet implemented")
    }


}