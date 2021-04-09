package com.dutch2019.repository

import android.app.Application
import android.location.Location
import android.util.Log
import com.dutch2019.db.AppDB
import com.dutch2019.db.LocationInfoDao
import com.dutch2019.db.RecentLocationDB
import com.dutch2019.model.LocationInfo

public class LocationRepository {

    // singleton으로 수정
    lateinit var locationInfoDao: LocationInfoDao
    lateinit var locationList: List<LocationInfo>
    lateinit var recentLocationList: List<LocationInfo>
    lateinit var db: AppDB
    lateinit var recentDB: RecentLocationDB

    suspend fun setRecentDB(application: Application) {
        recentDB = RecentLocationDB.getDatabase(application)!!
        locationInfoDao = recentDB.locationInfoDao()
        recentLocationList = locationInfoDao.getAll()
    }

    suspend fun setDB(application: Application) {
        db = AppDB.getDatabase(application)!!
        locationInfoDao = db.locationInfoDao()
        locationList = locationInfoDao.getAll()
    }

    suspend fun insertData(locationInfo: LocationInfo) {
        db.locationInfoDao().insert(locationInfo)
    }
    suspend fun insertRecentData(locationInfo: LocationInfo) {
        recentDB.locationInfoDao().insert(locationInfo)
    }

    suspend fun deleteAll() {
        db.locationInfoDao().deleteAll()
    }

    fun getLocationListData(): List<LocationInfo> {
        return locationList
    }
    fun getRecentLocationListData(): List<LocationInfo>{
        return recentLocationList
    }
}