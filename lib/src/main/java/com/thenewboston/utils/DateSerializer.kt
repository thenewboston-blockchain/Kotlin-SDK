package com.thenewboston.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class DateSerializer : KSerializer<LocalDateTime> {
    private val timeZone: TimeZone = TimeZone.UTC

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "Date", kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toInstant(timeZone).toString())
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val string = decoder.decodeString()
        return Instant.parse(string).toLocalDateTime(timeZone)
    }
}
