package com.dutch2019.model

import java.io.Serializable

data class LocationData(
    var locationName: String,
    var locationAddress: String,
    var latitude: Double,
    val longitude: Double

) : Serializable

