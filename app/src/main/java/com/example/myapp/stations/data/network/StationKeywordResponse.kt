package com.example.myapp.stations.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StationKeywordResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("keyword")
    val keyword: String,
    @SerialName("station_id")
    val stationId: Long,
)