package com.example.myapp.stations.data.di

import com.example.myapp.stations.data.datasource.local.StationsLocalDataSourceImpl
import com.example.myapp.stations.data.datasource.remote.StationsRemoteDataSourceImpl
import com.example.myapp.stations.domain.model.StationsLocalDataSource
import com.example.myapp.stations.domain.model.StationsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StationsDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsStationsLocalDataSource(
        stationsRemoteDataSource: StationsLocalDataSourceImpl
    ): StationsLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsStationsRemoteDataSource(
        stationsRemoteDataSourceImpl: StationsRemoteDataSourceImpl
    ): StationsRemoteDataSource
}