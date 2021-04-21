package com.dutch2019.repository

import android.app.Application
import com.dutch2019.db.LocationInfoDao
import com.dutch2019.db.RecentLocationDB
import com.dutch2019.model.LocationDataDB

class LocationRepository {

    private lateinit var locationInfoDao: LocationInfoDao
    lateinit var locationList: List<LocationDataDB>
    private lateinit var recentLocationList: List<LocationDataDB>
    private lateinit var recentDB: RecentLocationDB

    companion object {
        @Volatile
        private var instance: LocationRepository? = null

        @JvmStatic
        fun getInstance(): LocationRepository =
            instance ?: synchronized(this) {
                instance ?: LocationRepository().also {
                    instance = it
                }
            }
    }

    suspend fun setRecentDB(application: Application) {
        recentDB = RecentLocationDB.getDatabase(application)!!
        locationInfoDao = recentDB.locationInfoDao()
        recentLocationList = locationInfoDao.getAll()
    }

    suspend fun insertRecentData(locationData: LocationDataDB) {
        recentDB.locationInfoDao().insert(locationData)
    }

    suspend fun deleteAll() {
        recentDB.locationInfoDao().deleteAll()
    }

    suspend fun getLocationListData(): List<LocationDataDB> {
        return locationInfoDao.getAll()
    }

    suspend fun deleteLocationList(list: List<LocationDataDB>) {
        list.forEach {
            recentDB.locationInfoDao().delete(it)
        }
    }
}