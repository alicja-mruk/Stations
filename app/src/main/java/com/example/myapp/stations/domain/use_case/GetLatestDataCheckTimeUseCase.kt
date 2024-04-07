package com.example.myapp.stations.domain.use_case

import com.example.myapp.stations.data.db.model.DataCheckTmeEntity
import com.example.myapp.stations.domain.model.StationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestDataCheckTimeUseCase @Inject constructor(private val repository: StationsRepository) {
    suspend operator fun invoke(): Flow<DataCheckTmeEntity?> = repository.getLatestDataCheckTime()
}