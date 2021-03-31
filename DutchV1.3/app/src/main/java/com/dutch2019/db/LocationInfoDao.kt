package com.dutch2019.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dutch2019.model.LocationInfo


@Dao
interface LocationInfoDao {

    @Query("SELECT * FROM locationinfo")
    fun getAll() : List<LocationInfo>

    @Insert
    fun insert(info : LocationInfo)

    @Delete
    fun delete(info : LocationInfo)

    @Query("DELETE FROM locationinfo")
    fun deleteAll()

}