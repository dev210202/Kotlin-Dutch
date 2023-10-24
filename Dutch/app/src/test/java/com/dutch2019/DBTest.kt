package com.dutch2019

import android.location.Location
import com.dutch2019.model.LocationData
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertEquals
import org.junit.Test

class DBTest {
    @Test
    fun addDBTest(){
        val list = addDBData(LocationData(name = "123"), listOf(LocationData()))
        assertEquals(listOf(LocationData(name = "123"), LocationData()), list)
    }
}

fun addDBData(data: LocationData, testList : List<LocationData>) : List<LocationData> {
    var list = testList
    list?.let {
        list = mutableListOf(data).apply {
            addAll(it)
        }
    }
    return list
}