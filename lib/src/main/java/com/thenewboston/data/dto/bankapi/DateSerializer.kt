package com.thenewboston.data.dto.bankapi

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*

object DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: Date) {
        //ignored
    }

    override fun deserialize(decoder: Decoder): Date {
        val string = decoder.decodeString()
        val format = SimpleDateFormat("yyyy-mm-dd", Locale.ROOT)

        return format.parse(string)
    }
}