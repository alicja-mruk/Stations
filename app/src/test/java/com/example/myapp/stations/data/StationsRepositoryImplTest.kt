package com.example.myapp.stations.data

import com.example.myapp.core.data.network.NetworkManager
import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result
import com.example.myapp.stations.data.db.model.StationEntity
import com.example.myapp.stations.data.db.model.StationKeywordEntity
import com.example.myapp.stations.data.network.StationKeywordResponse
import com.example.myapp.stations.data.network.StationResponse
import com.example.myapp.stations.domain.model.Mapper
import com.example.myapp.stations.domain.model.Station
import com.example.myapp.stations.domain.model.StationKeyword
import com.example.myapp.stations.domain.model.StationsLocalDataSource
import com.example.myapp.stations.domain.model.StationsRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class StationsRepositoryImplTest {
    private val networkManager = mock<NetworkManager>()
    private val localDataSource = mock<StationsLocalDataSource>()
    private val remoteDataSource = mock<StationsRemoteDataSource>()
    private val stationEntityMapper = mock<Mapper<StationEntity, Station>>()
    private val stationResponseMapper = mock<Mapper<StationResponse, Station>>()
    private val stationKeywordEntityMapper = mock<Mapper<StationKeywordEntity, StationKeyword>>()
    private val stationKeywordResponseMapper =
        mock<Mapper<StationKeywordResponse, StationKeyword>>()

    private val repository = StationsRepositoryImpl(
        networkManager,
        localDataSource,
        remoteDataSource,
        stationEntityMapper,
        stationResponseMapper,
        stationKeywordEntityMapper,
        stationKeywordResponseMapper
    )

    @Test
    fun `getStations should return data from local data source when network is unavailable`() =
        runTest {
            whenever(networkManager.isNetworkAvailable()).thenReturn(false)
            val expectedLocalStations: Result<List<Station>, DataError> =
                Result.Success(localStations.map(stationEntityMapper::toModel))
            whenever(localDataSource.getStations()).thenReturn(Result.Success(localStations))

            val result = repository.getStations()

            assertEquals(expectedLocalStations, result)
            verify(localDataSource).getStations()
            verifyNoInteractions(remoteDataSource)
        }
}

private val localStations = listOf(
    StationEntity(
        1,
        "Station 1",
        "station-1",
        0.0,
        0.0,
        10,
        null,
        "City 1",
        "Region 1",
        "Country 1",
        null,
        false,
        true,
        true,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis()
    ),
    StationEntity(
        2,
        "Station 2",
        "station-2",
        0.0,
        0.0,
        5,
        null,
        "City 2",
        "Region 2",
        "Country 2",
        null,
        false,
        false,
        false,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis()
    )
)