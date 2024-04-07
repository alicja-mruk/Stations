package com.example.myapp.stations.domain.model

import com.example.myapp.core.domain.cache.CacheTime
import com.example.myapp.core.domain.cache.ExpirationModel

data class StationKeyword(
    val id: Long,
    val keyword: String,
    val stationId: Long,
    override val createdAt: Long,
    override val cacheTime: CacheTime = CacheTime.Day
): ExpirationModel