package com.dutch2019.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.dutch2019.model.LocationData

class Converters {

        @TypeConverter
        fun listToJson(value: List<LocationData>?) = Gson().toJson(value)

        @TypeConverter
        fun jsonToList(value: String) =
            Gson().fromJson(value, Array<LocationData>::class.java).toList()


}