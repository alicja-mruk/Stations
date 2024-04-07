package com.example.myapp.stations.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapp.core.domain.cache.CacheTime

@Entity(tableName = "stations")
data class StationEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "name_slug")
    val nameSlug: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double?,
    @ColumnInfo(name = "longitude")
    val longitude: Double?,
    @ColumnInfo(name = "hits")
    val hits: Int,
    @ColumnInfo(name = "ibnr")
    val ibnr: Int?,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "region")
    val region: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "localised_name")
    val localisedName: String?,
    @ColumnInfo(name = "is_group")
    val isGroup: Boolean,
    @ColumnInfo(name = "has_announcements")
    val hasAnnouncements: Boolean,
    @ColumnInfo(name = "is_nearby_station_enabled")
    val isNearbyStationEnabled: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
    @ColumnInfo(name = "cache_time")
    val cacheTime: CacheTime = CacheTime.Day
)