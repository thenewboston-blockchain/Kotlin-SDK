package com.thenewboston.common.http.config

data class BankConfig(
    val ipAddress: String,
    val port: String? = null,
    val protocol: String
)

data class ValidatorConfig(
    val ipAddress: String = "143.110.137.54",
    val port: String? = null,
    val protocol: String = "http"
)
