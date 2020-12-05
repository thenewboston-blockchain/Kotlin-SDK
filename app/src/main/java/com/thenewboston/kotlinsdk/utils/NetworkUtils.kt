package com.thenewboston.kotlinsdk.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.thenewboston.kotlinsdk.GENERAL_ERROR
import com.thenewboston.kotlinsdk.network.models.ValidatorConfigModel
import retrofit2.Response
import java.lang.Exception

class NetworkUtils {
    companion object {
        inline fun <reified T> callApiAndGetData(api: () -> Response<JsonElement>): Pair<String?, T?> {
            return try {
                val res = api()
                if(res.code() == 200)
                    Pair(null, Gson().fromJson(res.body(), T::class.java) as T)
                else
                    Pair(null, null)
            } catch (e: Exception) {
                Pair(e.message ?: GENERAL_ERROR, null)
            }
        }
    }
}
