package com.thenewboston.utils

object Pagination {
  var DEFAULT:PaginationOptions =  PaginationOptions(
      offset = 0, 
      limit = 20
    )
}

data class PaginationOptions(
  private val offset: Int, 
  private val limit: Int
){
   fun toQuery():String{
    return "?limit=${limit}?offset=${offset}"
    }
}