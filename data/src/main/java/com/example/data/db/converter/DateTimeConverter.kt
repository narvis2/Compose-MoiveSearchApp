package com.example.data.db.converter

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

class DateTimeConverter {
    @TypeConverter
    fun dateTimeToString(time: DateTime?): String {
        return timeFormatter.print(time)
    }

    @TypeConverter
    fun stringToDateTime(time: String?): DateTime? {
        return timeFormatter.parseDateTime(time)
    }

    companion object {
        private val timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(DateTimeZone.forID("Asia/Seoul"))
    }
}