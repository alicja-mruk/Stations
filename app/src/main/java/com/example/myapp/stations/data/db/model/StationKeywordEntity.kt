package com.example.myapp.stations.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapp.core.domain.cache.CacheTime

@Entity(tableName = "station_keywords")
data class StationKeywordEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "keyword")
    val keyword: String,
    @ColumnInfo(name = "station_id")
    val stationId: Long,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
    @ColumnInfo(name = "cache_time")
    val cacheTime: CacheTime = CacheTime.Day
)