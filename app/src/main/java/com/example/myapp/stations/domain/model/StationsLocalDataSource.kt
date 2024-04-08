package com.example.myapp.stations.domain.model

import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result
import com.example.myapp.stations.data.db.model.DataCheckTmeEntity
import com.example.myapp.stations.data.db.model.StationEntity
import com.example.myapp.stations.data.db.model.StationKeywordEntity
import kotlinx.coroutines.flow.Flow

interface StationsLocalDataSource {
    suspend fun getStations(): Result<List<StationEntity>, DataError.Database>
    suspend fun getStationById(id: Long): Result<StationEntity, DataError.Database>
    suspend fun getStationKeywords(): Result<List<StationKeywordEntity>, DataError.Database>
    suspend fun insertStations(stations: List<StationEntity>): Result<Unit, DataError.Database>
    suspend fun insertStationKeywords(stationKeywords: List<StationKeywordEntity>): Result<Unit, DataError.Database>
    suspend fun insertLatestDataCheckTime(timestamp: Long): Result<Unit, DataError.Database>
    suspend fun getLatestDataCheckTime(): Flow<DataCheckTmeEntity?>
}