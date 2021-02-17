package com.thenewboston.utils

object ConfirmationValidatorAPIEndpoints {

    fun validConfirmationBlocksEndpoint(blockID: String) =
        "/confirmation_blocks/$blockID/valid"

    fun queuedConfirmationBlocksEndpoint(blockID: String) =
        "/confirmation_blocks/$blockID/queued"
}
