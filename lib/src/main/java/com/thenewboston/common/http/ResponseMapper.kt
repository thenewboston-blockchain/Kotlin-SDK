package com.thenewboston.common.http

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Maps a JsonString to a specific class
 */
class ResponseMapper {
    inline fun <reified T> toObject(jsonText: String): T {
        return Json.decodeFromString(jsonText)
    }
}