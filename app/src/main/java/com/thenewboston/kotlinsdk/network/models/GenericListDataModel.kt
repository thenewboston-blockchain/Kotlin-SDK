package com.thenewboston.kotlinsdk.network.models

import com.google.gson.JsonElement

data class GenericListDataModel(
    val count: Int, // 5
    val next: String?, // http://.......?limit=30&offset=60
    val previous: String?, // http://......?limit=30&offset=60
    val results: ArrayList<JsonElement>
) {}
