package com.thenewboston.utils

data class PaginationResult<T> (
    val limit: Int?,
    val item: T
) {

    fun toList(): List<T> {
        val list: ArrayList<T> = arrayListOf()
        val range: Int = limit ?: 0
        for (i in 1..range) list.add(item)

        return list
    }
}
