package com.example.myapp.stations.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapp.stations.data.db.model.DataCheckTmeEntity
import com.example.myapp.stations.data.db.model.StationEntity
import com.example.myapp.stations.data.db.model.StationKeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StationsDao {
    @Query("SELECT * FROM stations")
    suspend fun getStations(): List<StationEntity>

    @Query("SELECT * FROM stations WHERE id=:id ")
    suspend fun getStationById(id: Long): StationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStations(stations: List<StationEntity>)

    @Query("SELECT * FROM station_keywords")
    suspend fun getStationKeywords(): List<StationKeywordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStationKeywords(stations: List<StationKeywordEntity>)

    @Insert
    suspend fun insertLatestDataCheckTime(dataCheckTmeEntity: DataCheckTmeEntity)

    @Query("SELECT * FROM data_check_time ORDER BY ID DESC LIMIT 1")
    fun getDataCheckTime(): Flow<DataCheckTmeEntity?>
}