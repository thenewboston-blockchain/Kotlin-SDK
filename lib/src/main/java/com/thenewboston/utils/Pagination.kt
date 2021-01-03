package com.thenewboston.utils

data class PaginationOptions(
    val offset: Int,
    val limit: Int
) {
    fun toQuery() = "?offset=$limit&limit=$limit"
}

object PAGE {
    var DEFAULT: PaginationOptions = PaginationOptions(
        offset = 0,
        limit = 20
    )

    var THIRTY_ITEMS: PaginationOptions = PaginationOptions(
        offset = 0,
        limit = 10
    )
}

data class PaginationResult<T> (
    val limit: Int,
    val item: T
) {

    fun toList(): List<T> {
        val list: ArrayList<T> = arrayListOf()
        for (i in 1..limit) list.add(item)

        return list
    }
}
