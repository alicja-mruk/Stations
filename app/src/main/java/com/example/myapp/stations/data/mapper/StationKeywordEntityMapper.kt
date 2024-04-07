package com.example.myapp.stations.data.mapper

import com.example.myapp.stations.data.db.model.StationKeywordEntity
import com.example.myapp.stations.domain.model.Mapper
import com.example.myapp.stations.domain.model.StationKeyword
import java.time.Instant
import javax.inject.Inject

class StationKeywordEntityMapper @Inject constructor() :
    Mapper<StationKeywordEntity, StationKeyword> {
    override fun toModel(value: StationKeywordEntity): StationKeyword = StationKeyword(
        id = value.id,
        keyword = value.keyword,
        stationId = value.stationId,
        createdAt = value.createdAt,
        cacheTime = value.cacheTime
    )

    override fun fromModel(value: StationKeyword): StationKeywordEntity = StationKeywordEntity(
        id = value.id,
        keyword = value.keyword,
        stationId = value.stationId,
        createdAt = Instant.now().toEpochMilli(),
        updatedAt = Instant.now().toEpochMilli(),
    )
}