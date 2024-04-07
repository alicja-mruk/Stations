package com.example.myapp.stations.data.mapper

import com.example.myapp.core.domain.cache.utils.isCacheDataExpired
import com.example.myapp.stations.data.network.StationKeywordResponse
import com.example.myapp.stations.domain.model.Mapper
import com.example.myapp.stations.domain.model.StationKeyword
import java.time.Instant
import javax.inject.Inject

class StationKeywordResponseMapper @Inject constructor() :
    Mapper<StationKeywordResponse, StationKeyword> {
    override fun toModel(value: StationKeywordResponse): StationKeyword = StationKeyword(
        id = value.id,
        keyword = value.keyword,
        stationId = value.stationId,
        createdAt = Instant.now().toEpochMilli()
    )

    override fun fromModel(value: StationKeyword): StationKeywordResponse = StationKeywordResponse(
        id = value.id,
        keyword = value.keyword,
        stationId = value.stationId
    )
}