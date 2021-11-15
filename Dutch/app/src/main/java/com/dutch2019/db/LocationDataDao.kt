package com.dutch2019.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dutch2019.model.LocationDBData

@Dao
interface LocationDataDao {

    @Query("SELECT * FROM locationdbdata")
    suspend fun getAll() : List<LocationDBData>

    @Insert
    suspend fun insert(data : LocationDBData)

    @Delete
    suspend fun delete(data : LocationDBData)

    @Query("DELETE FROM locationdbdata")
    suspend fun deleteAll()

}