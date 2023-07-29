package com.dutch2019.ui.middle

import android.content.ActivityNotFoundException
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.skt.Tmap.TMapPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dutch2019.model.LocationData
import com.dutch2019.model.MutableListLiveData
import com.dutch2019.model.StartEndPointData
import com.dutch2019.repository.FirebaseRepository
import com.dutch2019.repository.TMapRepository
import com.dutch2019.util.*
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.skt.Tmap.poi_item.TMapPOIItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MiddleViewModel @Inject constructor(
    private val tMapRepository: TMapRepository, private val firebaseRepository: FirebaseRepository
) : BaseViewModel() {

    private var locationList = listOf<LocationData>()

    private var centerPoint = TMapPoint(0.0, 0.0)
    private var ratioPoint = TMapPoint(0.0, 0.0)
    private var searchPoint = TMapPoint(0.0, 0.0)
    private var selectedLocationData = LocationData()
    private lateinit var imageUri: String

    private val _centerPointAddress = MutableLiveData<String>("")
    val centerPointAddress: LiveData<String> get() = _centerPointAddress

    private val _centerPointNearSubway = MutableLiveData<String>("")
    val centerPointNearSubway: LiveData<String> get() = _centerPointNearSubway

    private val _routeTime = MutableLiveData<String>("")
    val routeTime: LiveData<String> get() = _routeTime

    private val _facilityList = MutableListLiveData<LocationData>()
    val facilityList: LiveData<List<LocationData>> get() = _facilityList

    fun getLocationList(): List<LocationData> = locationList
    fun setLocationList(list: List<LocationData>) {
        locationList = list
    }

    fun getCenterPoint(): TMapPoint = centerPoint
    fun setCenterPoint(point: TMapPoint) {
        centerPoint = point
    }

    fun getRatioPoint(): TMapPoint = ratioPoint
    fun setRatioPoint(point: TMapPoint) {
        ratioPoint = point
    }

    fun getSearchPoint(): TMapPoint = searchPoint
    fun setSearchPoint(point: TMapPoint) {
        searchPoint = point
    }

    fun getSelectedLocation(): LocationData = selectedLocationData
    fun setSelectedLocation(locationData: LocationData) {
        selectedLocationData = locationData
    }

    fun getIndexToFacilityList(item: TMapPoint, locationName: String): Int {
        _facilityList.value!!.find {
            it.isSamePoint(item.latitude, item.longitude) && it.name == locationName
        }.apply {
            return _facilityList.value!!.indexOf(this)
        }
    }


    fun setCenterPointNearSubway(point: TMapPoint) {
        viewModelScope.launch(Dispatchers.IO) {
            _centerPointNearSubway.postValue(tMapRepository.getNearSubway(point))
        }
    }

    fun setRouteTime(point: TMapPoint, latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            StartEndPointData(point.longitude, point.latitude, longitude, latitude).apply {
                tMapRepository.getRouteTime(this).apply {
                    _routeTime.postValue(this)
                }
            }
        }
    }

    fun resetRouteTime() {
        _routeTime.value = ""
    }

    fun setCenterPointAddress(address: String) {
        _centerPointAddress.value = address
    }

    fun getCenterPointAddressValue(): String {
        return _centerPointAddress.value!!
    }

    fun searchCenterPointAddress(point: TMapPoint) {
        viewModelScope.launch(Dispatchers.IO) {
            tMapRepository.getAddress(point).apply {
                _centerPointAddress.postValue(this)
            }
        }
    }

    fun getFacilityList(): List<LocationData> = _facilityList.value!!


    fun searchNearFacility(point: TMapPoint, category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val nearFacilityList = mutableListOf<LocationData>()
            runCatching {
                tMapRepository.findNearFacility(point, category)
            }.onSuccess { result ->
                result?.let {
                    it.forEach { item ->
                        if (isItemDataOK(item)) {
                            nearFacilityList.add(LocationData().setNearFacilityItem(item))
                        }
                    }
                    getLocationList().forEach { locationData ->
                        nearFacilityList.find { listData ->
                            listData.isSameData(locationData)
                        }.apply {
                            nearFacilityList.remove(this)
                        }
                    }
                    _facilityList.postValue(nearFacilityList)
                }
            }.onFailure { throwable ->
                throw throwable
            }
        }
    }

    fun loadShareImage() {
        viewModelScope.launch(Dispatchers.IO) {
            imageUri = firebaseRepository.readImage()
        }
    }

    fun shareKakao(location: LocationData, context: Context) {

        val name = filtDoubleBlank(location.name)
        val address = filtDoubleBlank(location.address)
        val url = "https://map.kakao.com/link/map/$name,${location.lat},${location.lon}"

        val feedTemplate = FeedTemplate(
            content = Content(
                title = "더치가 중간지점을 찾아왔어요!",
                description = "$name\n$address",
                imageUrl = imageUri,
                link = Link(webUrl = url, mobileWebUrl = url)
            ), buttons = listOf(Button(title = "상세정보 보기", Link(webUrl = url, mobileWebUrl = url)))
        )
        LinkClient.instance.apply {
            if (isKakaoLinkAvailable(context)) {
                defaultTemplate(context, feedTemplate) { _, error ->
                    error?.let {
                        throw error
                    }
                }
            } else {
                WebSharerClient.instance.defaultTemplateUri(feedTemplate).apply {
                    try {
                        KakaoCustomTabsClient.openWithDefault(context, this)
                    } catch (error: Exception) {
                        // 카카오톡이 없고, 카카오링크가 안보내지는 경우
                        throw error
                    }
                    try {
                        KakaoCustomTabsClient.open(context, this)
                    } catch (error: Exception) {
                        throw error
                    }
                }
            }
        }
    }
}