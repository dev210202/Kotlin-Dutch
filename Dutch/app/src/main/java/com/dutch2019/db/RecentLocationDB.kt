package com.dutch2019.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dutch2019.model.LocationDBData


@Database(entities = [LocationDBData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecentLocationDB : RoomDatabase() {
    abstract fun locationDataDao() : LocationDataDao
}