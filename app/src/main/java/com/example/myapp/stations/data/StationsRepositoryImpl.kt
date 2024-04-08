package com.example.myapp.stations.data

import com.example.myapp.core.data.network.NetworkManager
import com.example.myapp.core.domain.cache.utils.isCacheDataExpired
import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result
import com.example.myapp.stations.data.db.model.DataCheckTmeEntity
import com.example.myapp.stations.data.db.model.StationEntity
import com.example.myapp.stations.data.db.model.StationKeywordEntity
import com.example.myapp.stations.data.network.StationKeywordResponse
import com.example.myapp.stations.data.network.StationResponse
import com.example.myapp.stations.domain.model.Mapper
import com.example.myapp.stations.domain.model.Station
import com.example.myapp.stations.domain.model.StationKeyword
import com.example.myapp.stations.domain.model.StationsLocalDataSource
import com.example.myapp.stations.domain.model.StationsRemoteDataSource
import com.example.myapp.stations.domain.model.StationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StationsRepositoryImpl @Inject constructor(
    private val networkManager: NetworkManager,
    private val localDataSource: StationsLocalDataSource,
    private val remoteDataSource: StationsRemoteDataSource,
    private val stationEntityMapper: Mapper<StationEntity, Station>,
    private val stationResponseMapper: Mapper<StationResponse, Station>,
    private val stationKeywordEntityMapper: Mapper<StationKeywordEntity, StationKeyword>,
    private val stationKeywordResponseMapper: Mapper<StationKeywordResponse, StationKeyword>
) : StationsRepository {
    override suspend fun getStations(): Result<List<Station>, DataError> {
        suspend fun getLocalStations(): Result<List<Station>, DataError.Database>{
            return when (val response = localDataSource.getStations()) {
                is Result.Success -> Result.Success(response.data.map(stationEntityMapper::toModel))
                is Result.Error -> Result.Error(response.error)
            }
        }

        suspend fun getRemoteStations(): Result<List<Station>, DataError.Network> {
            return when (val response = remoteDataSource.getStations()) {
                is Result.Success -> {
                    val stations = response.data.map(stationResponseMapper::toModel)
                    val stationsEntities = stations.map(stationEntityMapper::fromModel)
                    localDataSource.insertStations(stationsEntities)
                    Result.Success(stations)
                }

                is Result.Error -> {
                    Result.Error(response.error)
                }
            }
        }

        if (!networkManager.isNetworkAvailable()) {
            return getLocalStations()
        }

        return when (val localStationsResponse = getLocalStations()) {
            is Result.Success -> {
                val isDataExpired = localStationsResponse.data.any { isCacheDataExpired(it.createdAt , it.cacheTime) }
                if (isDataExpired) {
                    return when (val remoteStationsResponse = getRemoteStations()) {
                        is Result.Success -> Result.Success(remoteStationsResponse.data)
                        is Result.Error -> Result.Success(localStationsResponse.data)
                    }
                }
                Result.Success(localStationsResponse.data)
            }

            is Result.Error -> {
                if (localStationsResponse.error == DataError.Database.EMPTY_DB) {
                    return getRemoteStations()
                }
                Result.Error(localStationsResponse.error)
            }
        }
    }

    override suspend fun getStationById(id: Long?): Result<Station, DataError.Database> {
        return id?.let {
            return when (val response = localDataSource.getStationById(id)) {
                is Result.Success -> Result.Success(stationEntityMapper.toModel(response.data))
                is Result.Error -> Result.Error(response.error)
            }
        } ?: Result.Error(DataError.Database.ID_NOT_FOUND)
    }

    override suspend fun getStationKeywords(): Result<List<StationKeyword>, DataError> {
        suspend fun getLocalStationKeywords(): Result<List<StationKeyword>, DataError.Database> {
            return when (val response = localDataSource.getStationKeywords()) {
                is Result.Success -> Result.Success(response.data.map(stationKeywordEntityMapper::toModel))
                is Result.Error -> Result.Error(response.error)
            }
        }

        suspend fun getRemoteStationKeywords(): Result<List<StationKeyword>, DataError.Network> {
            return when (val response = remoteDataSource.getStationKeywords()) {
                is Result.Success -> {
                    val stationsKeywords =
                        response.data.map(stationKeywordResponseMapper::toModel)
                    val stationKeywordsEntities =
                        stationsKeywords.map(stationKeywordEntityMapper::fromModel)
                    localDataSource.insertStationKeywords(stationKeywordsEntities)
                    Result.Success(stationsKeywords)
                }

                is Result.Error -> {
                    Result.Error(response.error)
                }
            }
        }

        if (!networkManager.isNetworkAvailable()) {
            return getLocalStationKeywords()
        }

        return when (val localStationKeywordsResponse = getLocalStationKeywords()) {
            is Result.Success -> {
                val isDataExpired = localStationKeywordsResponse.data.any { isCacheDataExpired(it.createdAt, it.cacheTime) }
                if (isDataExpired) {
                    return when (val remoteStationKeywordsResponse = getRemoteStationKeywords()) {
                        is Result.Success -> Result.Success(remoteStationKeywordsResponse.data)
                        is Result.Error -> Result.Success(localStationKeywordsResponse.data)
                    }
                }
                Result.Success(localStationKeywordsResponse.data)
            }

            is Result.Error -> {
                if (localStationKeywordsResponse.error == DataError.Database.EMPTY_DB) {
                    return getRemoteStationKeywords()
                }
                Result.Error(localStationKeywordsResponse.error)
            }
        }
    }

    override suspend fun getLatestDataCheckTime(): Flow<DataCheckTmeEntity?> {
        return localDataSource.getLatestDataCheckTime()
    }
}