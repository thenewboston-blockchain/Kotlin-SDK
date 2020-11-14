package com.thenewboston.data.dto.bankapi

import kotlinx.datetime.*
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
