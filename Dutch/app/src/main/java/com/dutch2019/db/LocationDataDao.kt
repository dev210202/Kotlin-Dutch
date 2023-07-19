package com.dutch2019.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dutch2019.model.LocationSearchData

@Dao
interface LocationDataDao {

    @Query("SELECT * FROM locationdbdata")
    suspend fun getAll() : List<LocationSearchData>

    @Insert
    suspend fun insert(data : LocationSearchData)

    @Delete
    suspend fun delete(data : LocationSearchData)

    @Query("DELETE FROM locationdbdata")
    suspend fun deleteAll()

}