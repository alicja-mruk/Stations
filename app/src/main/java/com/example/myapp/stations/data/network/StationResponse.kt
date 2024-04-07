package com.example.myapp.stations.data.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class StationResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("name_slug")
    val nameSlug: String,
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?,
    @SerialName("hits")
    val hits: Int,
    @SerialName("ibnr")
    val ibnr: Int?,
    @SerialName("city")
    val city: String,
    @SerialName("region")
    val region: String,
    @SerialName("country")
    val country: String,
    @SerialName("localised_name")
    val localisedName: String?,
    @SerialName("is_group")
    val isGroup: Boolean,
    @SerialName("has_announcements")
    val hasAnnouncements: Boolean,
    @SerialName("is_nearby_station_enabled")
    val isNearbyStationEnabled: Boolean
)