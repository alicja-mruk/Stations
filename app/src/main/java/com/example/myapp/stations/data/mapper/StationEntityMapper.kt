package com.example.myapp.stations.data.mapper

import com.example.myapp.core.domain.cache.utils.isCacheDataExpired
import com.example.myapp.stations.data.db.model.StationEntity
import com.example.myapp.stations.domain.model.Mapper
import com.example.myapp.stations.domain.model.Station
import java.time.Instant
import javax.inject.Inject

class StationEntityMapper @Inject constructor() : Mapper<StationEntity, Station> {
    override fun toModel(value: StationEntity): Station = Station(
        id = value.id,
        name = value.name,
        nameSlug = value.nameSlug,
        latitude = value.latitude ?: 0.0,
        longitude = value.longitude ?: 0.0,
        hits = value.hits,
        ibnr = value.ibnr,
        city = value.city,
        region = value.region,
        country = value.country,
        localisedName = value.localisedName,
        isGroup = value.isGroup,
        hasAnnouncements = value.hasAnnouncements,
        isNearbyStationEnabled = value.isNearbyStationEnabled,
        createdAt = value.createdAt,
        cacheTime = value.cacheTime
    )

    override fun fromModel(value: Station): StationEntity = StationEntity(
        id = value.id,
        name = value.name,
        nameSlug = value.nameSlug,
        latitude = value.latitude,
        longitude = value.longitude,
        hits = value.hits,
        ibnr = value.ibnr,
        city = value.city,
        region = value.region,
        country = value.country,
        localisedName = value.localisedName,
        isGroup = value.isGroup,
        hasAnnouncements = value.hasAnnouncements,
        isNearbyStationEnabled = value.isNearbyStationEnabled,
        createdAt = Instant.now().toEpochMilli(),
        updatedAt = Instant.now().toEpochMilli(),
    )
}