package com.example.myapp.stations.domain.use_case

import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result
import com.example.myapp.stations.domain.model.StationsLocalDataSource
import javax.inject.Inject

class InsertLatestDataCheckTimeUseCase @Inject constructor(private val localDataSource: StationsLocalDataSource) {
    suspend operator fun invoke(timestamp: Long): Result<Unit, DataError.Database> =
        localDataSource.insertLatestDataCheckTime(timestamp)
}