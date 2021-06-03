package com.dutch2019.repository

import com.dutch2019.model.DetailData
import com.dutch2019.model.RouteTimeData
import com.dutch2019.model.StartEndPointData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class APIRepository : Repository() {

    companion object {
        @Volatile
        private var instance: APIRepository? = null

        @JvmStatic
        fun getInstance(): APIRepository =
            instance ?: synchronized(this) {
                instance ?: APIRepository().also {
                    instance = it
                }
            }
    }


    fun getRouteTime(startEndPointData: StartEndPointData): Observable<RouteTimeData> {
        return service.getRouteTime(startEndPointData).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDetailInfo(poiId: Int): Observable<DetailData> {
        return service.getDetailInfo(poiId.toString()).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}