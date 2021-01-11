package com.thenewboston.utils

data class PaginationOptions(
    val offset: Int?,
    val limit: Int?
) {
    constructor() : this(null, null)

    fun toQuery(): String {
        return when {
            offset == null || limit == null -> "?offset=0&limit=20"
            else -> "?offset=$offset&limit=$limit"
        }
    }
}
