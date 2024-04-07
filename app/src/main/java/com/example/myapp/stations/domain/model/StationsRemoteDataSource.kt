package com.example.myapp.stations.domain.model

import com.example.myapp.stations.data.network.StationKeywordResponse
import com.example.myapp.stations.data.network.StationResponse
import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result

interface StationsRemoteDataSource {
    suspend fun getStations(): Result<List<StationResponse>, DataError.Network>
    suspend fun getStationKeywords(): Result<List<StationKeywordResponse>, DataError.Network>
}