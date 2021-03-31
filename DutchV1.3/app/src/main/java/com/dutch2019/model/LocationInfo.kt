package com.dutch2019.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dutch2019.db.LocationInfoDao

@Entity
data class LocationInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "address")
    var adress: String,
    @ColumnInfo(name = "lat")
    var latitude: Double,
    @ColumnInfo(name = "lon")
    var longitude: Double
)
