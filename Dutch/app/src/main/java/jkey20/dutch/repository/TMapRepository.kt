package jkey20.dutch.repository

import android.util.Log
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import jkey20.dutch.BuildConfig
import jkey20.dutch.model.RouteDataList
import jkey20.dutch.model.StartEndPointData
import jkey20.dutch.model.ZipData
import java.lang.Exception

class TMapRepository : Repository() {

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
        return filtRouteTime(tMapService.getRouteTime(startEndPointData).body()!!)
    }

    suspend fun getZipCode(address : String) : String{
        return filtZipCode(tMapService.getZipCode("${BuildConfig.T_MAP_API}", address).body()!!)
    }

    private fun filtRouteTime(routeDataList: RouteDataList): String {
        return routeDataList.features[0].properties.totalTime
    }

    private fun filtZipCode(zipData : ZipData) : String{
        return zipData.coordinateInfo.coordinate[0].zipcode
    }

}