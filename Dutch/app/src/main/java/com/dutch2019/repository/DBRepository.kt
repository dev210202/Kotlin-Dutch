package com.dutch2019.repository

import com.dutch2019.db.RecentLocationDB
import com.dutch2019.model.LocationData

/*
    getRecentData()에서 reversed() 사용
    -> DB에서 데이터를 불러올때 시간순으로 누적되어서 최신순으로 정렬하려면 reversed 함수를 통해서 리스트를 역순으로 만들어야함.
 */
class DBRepository(private val recentDB: RecentLocationDB) {
    suspend fun getSearchData(): List<LocationData> =
        recentDB.locationDataDao().getAll().reversed()

    suspend fun insertSearchData(data: LocationData) = recentDB.locationDataDao().insert(data)

    suspend fun deleteRecentData(data: List<LocationData>){
        data.forEach{
            recentDB.locationDataDao().delete(it)
        }
    }

}