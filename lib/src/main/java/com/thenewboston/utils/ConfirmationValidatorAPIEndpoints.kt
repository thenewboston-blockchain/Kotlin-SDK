package com.thenewboston.utils

object ConfirmationValidatorAPIEndpoints {

    const val CONFIRMATION_BLOCKS_ENDPOINT = "/confirmation_blocks"

    const val BANK_CONFIRMATION_SERVICES = "/bank_confirmation_services"

    fun validConfirmationBlocksEndpoint(blockID: String) =
        "/confirmation_blocks/$blockID/valid"

    fun queuedConfirmationBlocksEndpoint(blockID: String) =
        "/confirmation_blocks/$blockID/queued"
}
