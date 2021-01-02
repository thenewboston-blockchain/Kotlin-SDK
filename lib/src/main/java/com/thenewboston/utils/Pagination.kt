package com.thenewboston.utils

data class PaginationOptions(
  val offset: Int, 
   val limit: Int
){
   fun toQuery():String{
    return "?limit=${limit}?offset=${offset}"
    }
}



object Page {
  var DEFAULT:PaginationOptions =  PaginationOptions(
      offset = 0, 
      limit = 20
    )

  var TEN_ITEMS:PaginationOptions =  PaginationOptions(
      offset = 0, 
      limit = 10
    )

  var PAGE_3:PaginationOptions =  PaginationOptions(
      offset = 40, 
      limit = 20
    )  

  var EMPTY:PaginationOptions =  PaginationOptions(
      offset = 0, 
      limit = 0
    )  

}

data class PaginationResult<T> ( 
   val limit:Int,
   val item: T
){

     fun toList(): List<T> { 
        val list:ArrayList<T> = arrayListOf()
        for (i in 1..limit){
        list.add(item)
        } 
      return list
    }  
}

