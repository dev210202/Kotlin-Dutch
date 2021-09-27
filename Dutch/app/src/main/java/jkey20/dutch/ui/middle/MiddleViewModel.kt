package jkey20.dutch.ui.middle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import jkey20.dutch.model.LocationData
import jkey20.dutch.repository.TMapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MiddleViewModel @Inject constructor(
    private val tMapRepository: TMapRepository
) : BaseViewModel() {

    private var locationList = ArrayList<LocationData>()

    private var centerPoint = TMapPoint(0.0, 0.0)

    private val _centerPointAddress = MutableLiveData<String>()
    val centerPointAddress: LiveData<String> get() = _centerPointAddress

    private val _centerPointNearSubway = MutableLiveData<String>()
    val centerPointNearSubway: LiveData<String> get() = _centerPointNearSubway

    fun setLocationList(list: ArrayList<LocationData>) {
        locationList = list
    }

    fun getLocationList(): ArrayList<LocationData> {
        return locationList
    }

    fun calculateCenterPoint(locationList: ArrayList<LocationData>): TMapPoint {
        var totalLat = 0.0
        var totalLon = 0.0
        locationList.forEach { data ->
            totalLat += data.lat
            totalLon += data.lon
        }
        return TMapPoint(
            totalLat / locationList.size,
            totalLon / locationList.size
        )
    }

    fun setCenterPoint(point: TMapPoint) {
        centerPoint = point
    }

    fun getCenterPoint(): TMapPoint = centerPoint


    fun setCenterPointAdress(point: TMapPoint) {
        viewModelScope.launch(Dispatchers.IO) {
            _centerPointAddress.postValue(tMapRepository.getAddress(point))

        }
    }

    fun setCenterPointNearSubway(point: TMapPoint) {
        viewModelScope.launch(Dispatchers.IO) {
            _centerPointNearSubway.postValue(tMapRepository.getNearSubway(point))
        }
    }

    fun getMiddleRouteTime(centerPoint: TMapPoint, latitude: Double, longitude: Double) {
        var startEndPointData = StartEndPointData(
            centerPoint.longitude,
            centerPoint.latitude,
            longitude,
            latitude
        )
        compositeDisposable.add(apiRepository.getRouteTime(startEndPointData).subscribe({ data ->
            var totalTime = data.features[0].properties.totalTime
            _totalRouteTime.postValue(convertTime(totalTime))
        }, { error ->
            Log.e("data error", error.message.toString())
        }))
    }
    fun resetRouteTime() {
        _totalRouteTime.postValue(" ")
    }


}