package com.thenewboston.utils

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
