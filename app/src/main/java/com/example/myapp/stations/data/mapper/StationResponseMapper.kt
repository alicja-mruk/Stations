package com.example.myapp.stations.data.mapper

import com.example.myapp.stations.data.network.StationResponse
import com.example.myapp.stations.domain.model.Mapper
import com.example.myapp.stations.domain.model.Station
import java.time.Instant
import javax.inject.Inject

class StationResponseMapper @Inject constructor() : Mapper<StationResponse, Station> {
    override fun toModel(value: StationResponse): Station = Station(
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
        createdAt = Instant.now().toEpochMilli()
    )

    override fun fromModel(value: Station): StationResponse = StationResponse(
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
        isNearbyStationEnabled = value.isNearbyStationEnabled
    )
}