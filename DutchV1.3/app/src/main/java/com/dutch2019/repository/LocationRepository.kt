package com.dutch2019.repository

import android.app.Application
import com.dutch2019.db.LocationInfoDao
import com.dutch2019.db.RecentLocationDB
import com.dutch2019.model.LocationDataDB

public class LocationRepository {

    // singleton으로 수정
    lateinit var locationInfoDao: LocationInfoDao
    lateinit var locationList: List<LocationDataDB>
    lateinit var recentLocationList: List<LocationDataDB>
    lateinit var recentDB: RecentLocationDB

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
}