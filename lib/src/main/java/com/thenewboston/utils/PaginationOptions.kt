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
