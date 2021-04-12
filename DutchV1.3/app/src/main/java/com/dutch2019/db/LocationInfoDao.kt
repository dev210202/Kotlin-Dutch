package com.dutch2019.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dutch2019.model.LocationDataDB
import com.dutch2019.model.LocationInfo


@Dao
interface LocationInfoDao {

    @Query("SELECT * FROM locationdatadb")
    suspend fun getAll() : List<LocationDataDB>

    @Insert
    suspend fun insert(info : LocationDataDB)

    @Delete
    suspend fun delete(info : LocationDataDB)

    @Query("DELETE FROM locationdatadb")
    suspend fun deleteAll()

}