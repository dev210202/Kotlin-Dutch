package com.dutch2019.repository

import android.util.Log
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.dutch2019.model.RouteDataList
import com.dutch2019.model.StartEndPointData
import com.dutch2019.network.TMapService
import com.skt.Tmap.poi_item.TMapPOIItem
import java.lang.Exception

class TMapRepository(private val api: TMapService) {

    fun findAll(input: String): ArrayList<TMapPOIItem>? = TMapData().findAllPOI(input)
    fun findNearFacility(point: TMapPoint, category: String): ArrayList<TMapPOIItem>? =
        TMapData().findAroundNamePOI(point, category, 3, 50)

    fun getAddress(point: TMapPoint): String {
        return try {
            TMapData().convertGpsToAddress(point.latitude, point.longitude)
        } catch (e: Exception) {
            "상세주소가 없습니다."
        }
    }

    fun getNearSubway(point: TMapPoint): String {
        return try {
            TMapData().findAroundNamePOI(point, "지하철", 20, 3)[0].poiName
        } catch (e: Exception) {
            "근처 지하철이 없습니다."
        }
    }

    suspend fun getRouteTime(startEndPointData: StartEndPointData): String {
        return try {
            filtRouteTime(api.getRouteTime(startEndPointData).body()!!)
        } catch (e: Exception) {
            Log.e("EXCEPTION", e.toString())
            ""
        }
    }

    private fun filtRouteTime(routeDataList: RouteDataList): String {
        return routeDataList.features[0].properties.totalTime
    }

}