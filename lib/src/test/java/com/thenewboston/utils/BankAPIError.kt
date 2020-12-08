package com.thenewboston.utils

import kotlinx.serialization.Serializable

@Serializable
data class BankAPIError(val statusCode: Int, val message: String)
