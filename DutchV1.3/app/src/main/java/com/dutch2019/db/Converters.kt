package com.dutch2019.db

import androidx.room.TypeConverter
import com.dutch2019.model.LocationInfo
import com.google.gson.Gson

class Converters {

        @TypeConverter
        fun listToJson(value: List<LocationInfo>?) = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) =
            Gson().fromJson(value, Array<LocationInfo>::class.java).toList()


}