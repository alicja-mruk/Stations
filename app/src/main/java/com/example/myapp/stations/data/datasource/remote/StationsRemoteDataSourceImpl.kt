package com.example.myapp.stations.data.datasource.remote

import com.example.myapp.core.data.network.utils.mapExceptionToNetworkError
import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result
import com.example.myapp.stations.data.network.StationKeywordResponse
import com.example.myapp.stations.data.network.StationResponse
import com.example.myapp.stations.domain.model.StationsRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class StationsRemoteDataSourceImpl @Inject constructor(private val httpClient: HttpClient) :
    StationsRemoteDataSource {
    override suspend fun getStations(): Result<List<StationResponse>, DataError.Network> {
        return try {
            // We shouldn't do that on frontend, but because BE returns invalid data it is required here to filter
            val result = httpClient.get("stations").body<List<StationResponse>>().filter {
                it.latitude != null && it.longitude != null
            }
            Result.Success(result)
        } catch (exception: Exception) {
            val networkError = mapExceptionToNetworkError(exception)
            return Result.Error(networkError)
        }
    }

    override suspend fun getStationKeywords(): Result<List<StationKeywordResponse>, DataError.Network> {
        return try {
            val result = httpClient.get("station_keywords").body<List<StationKeywordResponse>>()
            Result.Success(result)
        } catch (exception: Exception) {
            val networkError = mapExceptionToNetworkError(exception)
            return Result.Error(networkError)
        }
    }
}