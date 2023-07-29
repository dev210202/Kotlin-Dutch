package com.dutch2019.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dutch2019.model.LocationData


@Database(entities = [LocationData::class], version = 1, exportSchema = false)
abstract class RecentLocationDB : RoomDatabase() {
    abstract fun locationDataDao() : LocationDataDao
}