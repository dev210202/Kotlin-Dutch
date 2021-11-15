package com.dutch2019.repository

import android.util.Log
import com.dutch2019.db.RecentLocationDB
import com.dutch2019.model.LocationDBData

class DBRepository(private val recentDB: RecentLocationDB) {
    suspend fun getRecentData(): List<LocationDBData> =
        recentDB.locationDataDao().getAll()

    suspend fun insertRecentData(data: LocationDBData) = recentDB.locationDataDao().insert(data)

    suspend fun deleteRecentData(data: List<LocationDBData>){
        data.forEach{
            Log.i("data", it.centerAddress)
            recentDB.locationDataDao().delete(it)
        }
    }

}