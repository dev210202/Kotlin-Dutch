package com.dutch2019.util

import com.dutch2019.model.LocationDBData
import com.dutch2019.model.LocationDBDataList

inline fun Any?.isNotNull() : Boolean{
    return this != null
}
fun List<LocationDBData>.convertLocationDBDataToDataList() : LocationDBDataList {
    return LocationDBDataList(value = this)

}
fun List<LocationDBData>.sortByRecentList() : List<LocationDBData> {
    val newList = mutableListOf<LocationDBData>()

    for (i in this.size -1 downTo 0) {
        newList.add(this[i])
    }
    return newList
}