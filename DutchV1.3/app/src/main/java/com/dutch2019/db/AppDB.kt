package com.dutch2019.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dutch2019.model.LocationInfo

@Database(entities = [LocationInfo::class], version = 1)
public abstract class AppDB : RoomDatabase() {
    abstract fun locationInfoDao(): LocationInfoDao

    companion object {

        private  var instance: AppDB? = null

        fun getDatabase(context: Context): AppDB? {
            if (instance == null) {
                synchronized(AppDB::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDB::class.java, "locationinfo-db"
                        ).build()
                    }
                }
            }
            return instance
        }
    }
}

