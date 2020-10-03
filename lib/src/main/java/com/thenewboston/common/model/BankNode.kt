package com.thenewboston.common.model

sealed class BankNode(
    networkId: String,
    accountNumber: String,
    txFee: Int,
    protocol: String,
    ipAddress: String,
    port: String,
    trust: Float,
    version: String
) : Node(
    networkId = networkId,
    accountNumber = accountNumber,
    txFee = txFee,
    protocol = protocol,
    port = port,
    trust = trust,
    version = version,
    ipAddress = ipAddress
)