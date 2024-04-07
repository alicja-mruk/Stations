package com.example.myapp.core.data.db.converter

import androidx.room.TypeConverter
import com.example.myapp.core.domain.cache.CacheTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

class CacheTimeConverter {
    @TypeConverter
    fun fromString(value: String): CacheTime {
        return when {
            value.contains("NoExpiration") -> CacheTime.NoExpiration
            value.contains("Day") -> CacheTime.Day
            else -> {
                val minutes = value.substringAfter("Custom(").substringBefore(")").toInt()
                CacheTime.Custom(minutes)
            }
        }
    }

    @TypeConverter
    fun toString(cacheTime: CacheTime): String {
        return when (cacheTime) {
            is CacheTime.NoExpiration -> "NoExpiration"
            is CacheTime.Day -> "Day"
            is CacheTime.Custom -> "Custom(${cacheTime.minutes})"
        }
    }
}