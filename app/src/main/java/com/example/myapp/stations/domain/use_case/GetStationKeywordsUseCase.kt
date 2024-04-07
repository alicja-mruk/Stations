package com.example.myapp.stations.domain.use_case

import com.example.myapp.stations.domain.model.StationKeyword
import com.example.myapp.stations.domain.model.StationsRepository
import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result
import javax.inject.Inject

class GetStationKeywordsUseCase @Inject constructor(private val repository: StationsRepository) {
    suspend operator fun invoke(): Result<List<StationKeyword>, DataError> = repository.getStationKeywords()
}