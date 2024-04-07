package com.example.myapp.core.domain.cache.utils

import com.example.myapp.core.domain.cache.CacheTime
import java.time.Instant

fun isCacheDataExpired(timestamp: Long, cacheTime: CacheTime): Boolean {
    if (cacheTime == CacheTime.NoExpiration) return false

    val millisecondsLeft = Instant.now().toEpochMilli() - timestamp
    val seconds = millisecondsLeft / 1000

    return seconds >= cacheTime.minutes * SECONDS_IN_MINUTE
}

const val SECONDS_IN_MINUTE = 60