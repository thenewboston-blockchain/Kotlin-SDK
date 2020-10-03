package com.thenewboston.common.model

sealed class ValidatorNode(
    networkId: String,
    accountNumber: String,
    txFee: Int,
    protocol: String,
    ipAddress: String,
    port: String,
    trust: Float,
    version: String,
    val dailyRate: String,
    val rootAccountFile: String,
    val rootAccountFileHash: String,
    val seedBlockIdentifier: String
) : Node(
    networkId = networkId,
    accountNumber = accountNumber,
    txFee = txFee,
    protocol = protocol,
    ipAddress = ipAddress,
    port = port,
    trust = trust,
    version = version
)