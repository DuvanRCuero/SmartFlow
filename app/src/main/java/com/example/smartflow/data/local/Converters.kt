package com.example.smartflow.data.local

import androidx.room.TypeConverter
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class Converters {
    private val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun toUUID(uuidString: String): UUID {
        return UUID.fromString(uuidString)
    }

    @TypeConverter
    fun fromZonedDateTime(zonedDateTime: ZonedDateTime): String {
        return zonedDateTime.format(formatter)
    }

    @TypeConverter
    fun toZonedDateTime(zonedDateTimeString: String): ZonedDateTime {
        return ZonedDateTime.parse(zonedDateTimeString, formatter)
    }
}