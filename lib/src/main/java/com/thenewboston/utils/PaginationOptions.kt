package com.thenewboston.utils

data class PaginationOptions(
    val offset: Int?,
    val limit: Int?
) {
    fun toQuery(): String {
        return when {
            offset == null || limit == null -> "?offset=0&limit=20"
            else -> "?offset=$offset&limit=$limit"
        }
    }
}

object PAGE {
    var DEFAULT: PaginationOptions = PaginationOptions(
        offset = 0,
        limit = 20
    )

    var PAGE_2: PaginationOptions = PaginationOptions(
        offset = 20,
        limit = 20
    )

    var THIRTY_ITEMS: PaginationOptions = PaginationOptions(
        offset = 0,
        limit = 30
    )
}
