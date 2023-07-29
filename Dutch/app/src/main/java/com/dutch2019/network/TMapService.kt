package com.dutch2019.network

import com.dutch2019.BuildConfig
import com.dutch2019.model.RouteDataList
import com.dutch2019.model.StartEndPointData
import retrofit2.Response
import retrofit2.http.*

interface TMapService {
    @POST("tmap/routes?version=1&appKey=${BuildConfig.T_MAP_API}")
    suspend fun getRouteTime(@Body startEndPointData: StartEndPointData): Response<RouteDataList>

}