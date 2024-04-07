package com.example.myapp.stations.domain.model

import com.example.myapp.core.domain.cache.CacheTime
import com.example.myapp.core.domain.cache.ExpirationModel

data class Station(
    val id: Long,
    val name: String,
    val nameSlug: String,
    val latitude: Double,
    val longitude: Double,
    val hits: Int,
    val ibnr: Int?,
    val city: String,
    val region: String,
    val country: String,
    val localisedName: String?,
    val isGroup: Boolean,
    val hasAnnouncements: Boolean,
    val isNearbyStationEnabled: Boolean,
    override val createdAt: Long,
    override val cacheTime: CacheTime = CacheTime.Day
): ExpirationModel