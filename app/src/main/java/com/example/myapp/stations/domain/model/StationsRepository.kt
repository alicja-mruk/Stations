package com.example.myapp.stations.domain.model

import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result
import com.example.myapp.stations.data.db.model.DataCheckTmeEntity
import kotlinx.coroutines.flow.Flow

interface StationsRepository {
    suspend fun getStations(): Result<List<Station>, DataError>
    suspend fun getStationById(id: Long): Result<Station, DataError>
    suspend fun getStationKeywords(): Result<List<StationKeyword>, DataError>
    suspend fun getLatestDataCheckTime(): Flow<DataCheckTmeEntity?>
}