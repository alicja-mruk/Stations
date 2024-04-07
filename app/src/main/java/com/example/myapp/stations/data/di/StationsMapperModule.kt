package com.example.myapp.stations.data.di

import com.example.myapp.stations.data.db.model.StationEntity
import com.example.myapp.stations.data.db.model.StationKeywordEntity
import com.example.myapp.stations.data.mapper.StationEntityMapper
import com.example.myapp.stations.data.mapper.StationKeywordEntityMapper
import com.example.myapp.stations.data.mapper.StationKeywordResponseMapper
import com.example.myapp.stations.data.mapper.StationResponseMapper
import com.example.myapp.stations.data.network.StationKeywordResponse
import com.example.myapp.stations.data.network.StationResponse
import com.example.myapp.stations.domain.model.Mapper
import com.example.myapp.stations.domain.model.Station
import com.example.myapp.stations.domain.model.StationKeyword
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StationsMapperModule {
    @Binds
    @Singleton
    abstract fun bindsStationResponseMapper(
        stationResponseMapper: StationResponseMapper
    ): Mapper<StationResponse, Station>

    @Binds
    @Singleton
    abstract fun bindsStationEntityMapper(
        stationResponseMapper: StationEntityMapper
    ): Mapper<StationEntity, Station>

    @Binds
    @Singleton
    abstract fun bindsStationKeywordResponseMapper(
        stationResponseMapper: StationKeywordResponseMapper
    ): Mapper<StationKeywordResponse, StationKeyword>

    @Binds
    @Singleton
    abstract fun bindsStationKeywordEntityMapper(
        stationResponseMapper: StationKeywordEntityMapper
    ): Mapper<StationKeywordEntity, StationKeyword>
}