package com.thenewboston.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DateSerializerTest {
    val date = "2021-07-08T02:14:59.307535Z"

    val event = Event(
        title = "APPLE WWDC",
        date = parseDateTime(date)
    )

    val person = Person(
        name = "John Doe",
        dob = parseDateTime(date)
    )

    val personJsonString =
        """{"name":"John Doe","dob":"$date"}""".trimIndent()

    val eventJsonString =
        """{"title":"${event.title}","date": "$date"}""".trimIndent()

    @Test
    fun `should return a properly deserialized person from JSON`() {
        val deserializedPerson: Person = Json.decodeFromString(personJsonString)

        assertEquals(person.name, deserializedPerson.name)
        assertEquals(person.dob, deserializedPerson.dob)
    }

    @Test
    fun `should throw an exception when deserializing an event into JSON`() {
        assertThrows<SerializationException> {
            Json.encodeToString(event)
        }
    }

    @Test
    fun `should throw an exception when deserializing a JSON into an event`() {
        assertThrows<SerializationException> {
            Json.decodeFromString(eventJsonString)
        }
    }

    @Test
    fun `should serialize a person to json string`() {
        val personJson = Json.encodeToString(person)
        assertEquals(personJsonString, personJson)
    }

    @Serializable
    data class Person(
        @SerialName("name")
        val name: String,

        @Serializable(with = DateSerializer::class)
        @SerialName("dob")
        val dob: LocalDateTime
    )

    @Serializable
    data class Event(
        @SerialName("title")
        val title: String,

        @Contextual
        @SerialName("date")
        val date: LocalDateTime
    )

    private fun parseDateTime(string: String): LocalDateTime {
        return Instant.parse(string).toLocalDateTime(timeZone = TimeZone.UTC)
    }
}
