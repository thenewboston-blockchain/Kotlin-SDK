package com.thenewboston.utils

data class PaginationOptions(
  private val offset: Int , 
  private val limit: Int
){
   fun asQuery():String{
    return "?limit=${limit}?offset=${offset}"
    }
}