package com.dutch2019.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dutch2019.model.LocationData
import com.dutch2019.model.LocationSearchData


@Database(entities = [LocationData::class], version = 1, exportSchema = false)
abstract class RecentLocationDB : RoomDatabase() {
    abstract fun locationDataDao() : LocationDataDao
}