package com.dutch2019.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
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
    private val _searchPOIItemList = MutableListLiveData<TMapPOIItem>()
    val searchPOIItemList: LiveData<List<TMapPOIItem>> get() = _searchPOIItemList
    fun search(input: String, showToast: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            tMapRepository.findAll(input).apply {
                if (this.isNotNull()) {
                    _searchPOIItemList.postValue(this)
                } else {
                    showToast("검색된 위치가 없습니다.")
                }
            }
        }
    }

    fun clearSearchItemList() {
        _searchPOIItemList.clear()
    }
}