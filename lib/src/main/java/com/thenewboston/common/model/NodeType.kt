package com.thenewboston.common.model

enum class NodeType(value: String) {
    BANK("BANK"),
    VALIDATOR("VALIDATOR"),
    PRIMARY_VALIDATOR("PRIMARY_VALIDATOR"),
    CONFIRMATION_VALIDATOR("CONFIRMATION_VALIDATOR")
}