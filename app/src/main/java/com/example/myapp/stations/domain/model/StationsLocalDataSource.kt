package com.example.myapp.stations.domain.model

import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result
import com.example.myapp.stations.data.db.model.DataCheckTmeEntity
import com.example.myapp.stations.data.db.model.StationEntity
import com.example.myapp.stations.data.db.model.StationKeywordEntity
import kotlinx.coroutines.flow.Flow

interface StationsLocalDataSource {
    suspend fun getStations(): Result<List<StationEntity>, DataError.Local>
    suspend fun getStationById(id: Long): Result<StationEntity, DataError.Local>
    suspend fun getStationKeywords(): Result<List<StationKeywordEntity>, DataError.Local>
    suspend fun insertStations(stations: List<StationEntity>): Result<Unit, DataError.Local>
    suspend fun insertStationKeywords(stationKeywords: List<StationKeywordEntity>): Result<Unit, DataError.Local>
    suspend fun insertLatestDataCheckTime(timestamp: Long): Result<Unit, DataError.Local>
    suspend fun getLatestDataCheckTime(): Flow<DataCheckTmeEntity?>
}