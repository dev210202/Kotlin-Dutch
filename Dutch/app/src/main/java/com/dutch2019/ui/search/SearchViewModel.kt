package com.dutch2019.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationData
import com.dutch2019.model.LocationSearchData
import com.dutch2019.model.MutableListLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dutch2019.repository.TMapRepository
import com.dutch2019.util.isNotNull
import com.skt.Tmap.poi_item.TMapPOIItem
import kotlinx.coroutines.Dispatchers
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

    private val _searchLocationList = MutableListLiveData<LocationData>()
    val searchLocationList: LiveData<List<LocationData>> get() = _searchLocationList
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

    fun setSearchLocationList(list: List<LocationData>) {
        _searchLocationList.value = list
    }
    fun getSearchLocationList() : List<LocationData> {
        return _searchLocationList.value!!
    }
}