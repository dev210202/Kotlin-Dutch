package com.dutch2019.repository

import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.dutch2019.BuildConfig
import com.dutch2019.model.RouteDataList
import com.dutch2019.model.StartEndPointData
import com.dutch2019.model.ZipData
import com.dutch2019.network.TMapService
import java.lang.Exception

class TMapRepository(private val api: TMapService) {

    fun findAll(input: String): ArrayList<TMapPOIItem>? {
        return TMapData().findAllPOI(input)
    }

    fun getAddress(point: TMapPoint): String {
        try {
            return TMapData().convertGpsToAddress(point.latitude, point.longitude)
        } catch (e: Exception) {
            return "상세주소가 없습니다."
        }
    }

    fun getNearSubway(point: TMapPoint): String {
        val tMapPOIItems = TMapData().findAroundNamePOI(
            point,
            "지하철",
            20,
            3
        )
        return if (tMapPOIItems.isEmpty()) {
            "근처 지하철이 없습니다"
        } else {
            tMapPOIItems[0].poiName
        }
    }

    suspend fun getRouteTime(startEndPointData: StartEndPointData): String {
        return filtRouteTime(api.getRouteTime(startEndPointData).body()!!)
    }

    private fun filtRouteTime(routeDataList: RouteDataList): String {
        return routeDataList.features[0].properties.totalTime
    }

    private fun filtZipCode(zipData : ZipData) : String{
        return zipData.coordinateInfo.coordinate[0].zipcode
    }

}