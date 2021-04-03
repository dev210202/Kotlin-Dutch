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

    fun setRecentDB(application: Application) {
        recentDB = RecentLocationDB.getDatabase(application)!!
        locationInfoDao = recentDB.locationInfoDao()
        recentLocationList = locationInfoDao.getAll()
    }

    fun setDB(application: Application) {
        db = AppDB.getDatabase(application)!!
        locationInfoDao = db.locationInfoDao()
        locationList = locationInfoDao.getAll()
    }

    fun insertData(locationInfo: LocationInfo) {
        db.locationInfoDao().insert(locationInfo)
    }
    fun insertRecentData(locationInfo: LocationInfo) {
        recentDB.locationInfoDao().insert(locationInfo)
    }

    fun deleteAll() {
        db.locationInfoDao().deleteAll()
    }

    fun getLocationListData(): List<LocationInfo> {
        return locationList
    }
    fun getRecentLocationListData(): List<LocationInfo>{
        return recentLocationList
    }
}