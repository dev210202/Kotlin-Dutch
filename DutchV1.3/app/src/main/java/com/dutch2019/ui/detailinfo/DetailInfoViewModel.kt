package com.dutch2019.ui.detailinfo

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.DetailData
import com.dutch2019.model.LocationInfo
import com.dutch2019.repository.APIRepository
import com.skt.Tmap.TMapPoint

class DetailInfoViewModel : BaseViewModel() {

    private val _detailInfo = MutableLiveData<DetailData>()
    val detailInfo: LiveData<DetailData> get() = _detailInfo

    var point = LocationInfo()
    var apiRepository = APIRepository.getInstance()

    fun getDetailInfo(poiId: Int) {
        compositeDisposable.add(apiRepository.getDetailInfo(poiId).subscribe({ data ->
            _detailInfo.postValue(data)
        }, { error ->
            Log.e("getDetailInfo error", error.message.toString())
        }))
    }

    fun filterText(value: String): String {
        var result = ""
        var splitList = value.split(";")

        for (i in splitList.indices) {
            result += if (!splitList[i].contains("[")&& splitList[i] != "") {
                "," +  splitList[i]
            }
            else{
                "\n"+ splitList[i]
            }

    }
    return result
}
}