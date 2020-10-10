package com.thenewboston.common.model

data class BankConfig(
    val ipAddress: String,
    val port: String,
    val protocol: String
) {

    companion object {
        /**
         * For now this is the default config to connect to the
         * currently deployed bank
         */
        val default: BankConfig
            get() {
                return BankConfig(
                    ipAddress = "143.110.137.54",
                    port = "80",
                    protocol = "http"
                )
            }
    }
}
