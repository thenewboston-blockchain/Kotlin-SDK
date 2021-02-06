package com.thenewboston.utils

data class PaginationOptions(
    val offset: Int?,
    val limit: Int?
) {
    fun toQuery(): String = "?offset=$offset&limit=$limit"
}
