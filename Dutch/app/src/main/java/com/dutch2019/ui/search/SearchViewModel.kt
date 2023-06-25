package com.dutch2019.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.MutableListLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dutch2019.repository.TMapRepository
import com.dutch2019.util.isNotNull
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

    fun search(input: String, errorToast: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultList = tMapRepository.findAll(input)
            if (resultList.isNotNull()) {
                _tMapPOIItemList.postValue(resultList!!)
            } else {
                MainScope().launch {
                    errorToast("TMap API 통신 오류입니다.")
                }
            }
        }
    }

    fun setInputValue(input: String) {
        _inputValue.value = input
    }
}