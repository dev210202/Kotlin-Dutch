package jkey20.dutch.network

import io.reactivex.Observable
import jkey20.dutch.BuildConfig
import jkey20.dutch.model.RouteDataList
import jkey20.dutch.model.StartEndPointData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TMapService {
    @POST("routes?version=1&appKey=${BuildConfig.TMAP_API}")
    fun getRouteTime(@Body startEndPointData: StartEndPointData) : RouteDataList
}