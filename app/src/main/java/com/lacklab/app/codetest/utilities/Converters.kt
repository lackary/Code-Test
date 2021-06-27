package com.lacklab.app.codetest.utilities

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }

    @TypeConverter
    fun datestampToCalendar(value: Long?): Calendar? {
        return if(value == null) null else
                Calendar.getInstance().apply { timeInMillis = value }
    }

}