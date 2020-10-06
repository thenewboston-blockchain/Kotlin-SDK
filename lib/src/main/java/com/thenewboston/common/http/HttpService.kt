package com.thenewboston.common.http

import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
internal class HttpService(private val networkClient: NetworkClient) {

    suspend fun doGet(path: String): HttpResponse = networkClient.client.get(
        "/$path"
    )

    suspend fun doPost(path: String): HttpResponse = networkClient.client.post(path)

    suspend fun doDelete(path: String): HttpResponse = networkClient.client.delete(path)

    suspend fun doPut(path: String): HttpResponse = networkClient.client.put(path)
}
