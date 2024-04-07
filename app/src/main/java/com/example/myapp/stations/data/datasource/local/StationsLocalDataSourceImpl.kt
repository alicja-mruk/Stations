package com.example.myapp.stations.data.datasource.local

import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result
import com.example.myapp.stations.data.db.StationsDao
import com.example.myapp.stations.data.db.model.DataCheckTmeEntity
import com.example.myapp.stations.data.db.model.StationEntity
import com.example.myapp.stations.data.db.model.StationKeywordEntity
import com.example.myapp.stations.domain.model.StationsLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StationsLocalDataSourceImpl @Inject constructor(private val stationsDao: StationsDao) :
    StationsLocalDataSource {
    override suspend fun getStations(): Result<List<StationEntity>, DataError.Local> {
        // We shouldn't do that on frontend, but because BE returns invalid data it is required here to filter
        val result = stationsDao.getStations().filter {
            it.longitude != null && it.latitude != null
        }

        if (result.isEmpty()) return Result.Error(DataError.Local.EMPTY_DB)
        return Result.Success(result)
    }

    override suspend fun getStationById(id: Long): Result<StationEntity, DataError.Local> {
        return Result.Success(stationsDao.getStationById(id))
    }

    override suspend fun getStationKeywords(): Result<List<StationKeywordEntity>, DataError.Local> {
        val result = stationsDao.getStationKeywords()

        if (result.isEmpty()) return Result.Error(DataError.Local.EMPTY_DB)
        return Result.Success(result)
    }

    override suspend fun insertStations(stations: List<StationEntity>): Result<Unit, DataError.Local> {
        return Result.Success(stationsDao.insertStations(stations))
    }

    override suspend fun insertStationKeywords(stationKeywords: List<StationKeywordEntity>): Result<Unit, DataError.Local> {
        return Result.Success(stationsDao.insertStationKeywords(stationKeywords))
    }

    override suspend fun insertLatestDataCheckTime(timestamp: Long): Result<Unit, DataError.Local> {
        return Result.Success(stationsDao.insertLatestDataCheckTime(DataCheckTmeEntity(timestamp = timestamp)))
    }

    override suspend fun getLatestDataCheckTime(): Flow<DataCheckTmeEntity?> {
        return stationsDao.getDataCheckTime()
    }
}