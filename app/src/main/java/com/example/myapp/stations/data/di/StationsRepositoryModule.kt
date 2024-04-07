package com.example.myapp.stations.data.di

import com.example.myapp.stations.data.StationsRepositoryImpl
import com.example.myapp.stations.domain.model.StationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StationsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsStationsRepository(
        stationsRepositoryImpl: StationsRepositoryImpl
    ): StationsRepository
}