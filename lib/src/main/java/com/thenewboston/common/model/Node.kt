package com.thenewboston.common.model

abstract class Node(
    val networkId: String,
    val accountNumber: String,
    val txFee: Int,
    val protocol: String,
    val ipAddress: String,
    val port: String,
    val trust: Float,
    val version: String
) {

}