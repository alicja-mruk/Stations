package com.example.myapp.core.data.di

import android.content.Context
import androidx.room.Room
import com.example.myapp.core.data.db.AppDatabase
import com.example.myapp.stations.data.db.StationsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }

    @Provides
    fun provideStationsDao(appDatabase: AppDatabase): StationsDao {
        return appDatabase.stationsDao()
    }
}