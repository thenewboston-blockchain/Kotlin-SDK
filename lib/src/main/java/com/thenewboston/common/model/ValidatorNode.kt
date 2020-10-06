package com.thenewboston.common.model

import java.math.BigDecimal
import kotlinx.serialization.SerialName

/**
 * Contains the meta information about the Validator Node
 *
 * @param networkId the id of the network
 * @param accountNumber the account number related to this validator
 * @param txFee the tax fee of the validator
 * @param protocol the protocol the node speaks, e.g. HTTP
 * @param ipAddress the physical ip address of the validator
 * @param port the port the validator runs on
 * @param trust the level of trust (0.0 - 100.0)
 * @param version the current version, e.g. "v1.0"
 * @param dailyRate the daily rate of the validator
 * @param rootAccountFile the path to the account file
 *@param rootAccountFileHash the account file hashed
 */
sealed class ValidatorNode(
    networkId: String,
    accountNumber: String,
    txFee: Int,
    protocol: String,
    ipAddress: String,
    port: String,
    trust: Float,
    version: String,
    @SerialName("daily_rate")
    val dailyRate: BigDecimal,
    @SerialName("root_account_file")
    val rootAccountFile: String,
    @SerialName("root_account_file_hash")
    val rootAccountFileHash: String,
    @SerialName("seed_block_identifier")
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
