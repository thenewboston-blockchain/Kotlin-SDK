package com.thenewboston.data.dto.bankapi

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "Date", kind = PrimitiveKind.LONG
    )

    override fun serialize(encoder: Encoder, value: Date) {
        // ignored
    }

    override fun deserialize(decoder: Decoder): Date {
        val string = decoder.decodeString()
        val format = SimpleDateFormat("yyyy-mm-dd", Locale.ROOT)

        return format.parse(string)
    }
}
