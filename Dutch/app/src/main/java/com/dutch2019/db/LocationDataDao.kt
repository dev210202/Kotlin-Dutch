package com.dutch2019.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dutch2019.model.LocationData
import com.dutch2019.model.LocationSearchData

@Dao
interface LocationDataDao {

    @Query("SELECT * FROM locationdata")
    suspend fun getAll() : List<LocationData>

    @Insert
    suspend fun insert(data : LocationData)

    @Delete
    suspend fun delete(data : LocationData)

    @Query("DELETE FROM locationdata")
    suspend fun deleteAll()

}