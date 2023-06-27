package com.dutch2019.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class LocationDBDataList(
    val value: List<LocationDBData>
) : Serializable {
    fun convertLocationDBDataListToData(): List<LocationDBData> {
        return value
    }
}

