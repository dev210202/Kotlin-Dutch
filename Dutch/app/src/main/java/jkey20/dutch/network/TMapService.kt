package jkey20.dutch.network

import io.reactivex.Observable
import jkey20.dutch.BuildConfig
import jkey20.dutch.model.RouteDataList
import jkey20.dutch.model.StartEndPointData
import jkey20.dutch.model.ZipData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface TMapService {
    @POST("tmap/routes?version=1&appKey=${BuildConfig.T_MAP_API}")
    suspend fun getRouteTime(@Body startEndPointData: StartEndPointData): Response<RouteDataList>

    @GET("tmap/geo/postcode?version=1")
    suspend fun getZipCode(
        @Query("appKey") appKey: String,
        @Query("addr") addr: String,
    ) : Response<ZipData>
}