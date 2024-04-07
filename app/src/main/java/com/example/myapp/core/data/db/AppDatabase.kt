package com.example.myapp.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapp.core.data.db.converter.CacheTimeConverter
import com.example.myapp.stations.data.db.StationsDao
import com.example.myapp.stations.data.db.model.DataCheckTmeEntity
import com.example.myapp.stations.data.db.model.StationEntity
import com.example.myapp.stations.data.db.model.StationKeywordEntity

@Database(
    entities = [StationEntity::class, StationKeywordEntity::class, DataCheckTmeEntity::class],
    version = 1
)
@TypeConverters(CacheTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationsDao(): StationsDao
}