package com.thenewboston.utils.mocks

import kotlinx.serialization.Serializable

@Serializable
data class BankAPIError(val statusCode: Int, val message: String)
