package com.dutch2019.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationDBData
import com.dutch2019.model.MutableListLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dutch2019.repository.TMapRepository
import com.dutch2019.util.isNotNull
import com.dutch2019.util.toast
import com.skt.Tmap.poi_item.TMapPOIItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val tMapRepository: TMapRepository
) : BaseViewModel() {
    private val _tMapPOIItemList = MutableListLiveData<TMapPOIItem>()
    val tMapPOIItemList: LiveData<List<TMapPOIItem>> get() = _tMapPOIItemList

    private val _inputValue = MutableLiveData<String>()
    val inputValue: LiveData<String> get() = _inputValue

    private val _recentLocationList = MutableListLiveData<LocationDBData>()
    val recentLocationList: LiveData<List<LocationDBData>> get() = _recentLocationList
    fun search(input: String, showToast: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultList = tMapRepository.findAll(input)
            if (resultList.isNotNull()) {
                _tMapPOIItemList.postValue(resultList!!)
            } else {
                showToast("검색된 위치가 없습니다.")
            }
        }
    }

    fun setInputValue(input: String) {
        _inputValue.value = input
    }

    fun setRecentLocationList(list: List<LocationDBData>) {
        _recentLocationList.value = list
    }
    fun getRecentLocationList() : List<LocationDBData> {
        return _recentLocationList.value!!
    }
}