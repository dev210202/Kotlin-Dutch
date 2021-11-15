package com.dutch2019.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.skt.Tmap.TMapPOIItem
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dutch2019.repository.TMapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val tMapRepository: TMapRepository
) : BaseViewModel() {
    private val _locationList = MutableLiveData<ArrayList<TMapPOIItem>>(ArrayList())
    val locationList: LiveData<ArrayList<TMapPOIItem>> get() = _locationList

    private val _inputValue = MutableLiveData<String>()
    val inputValue: LiveData<String> get() = _inputValue

    fun search(input: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _locationList.postValue(tMapRepository.findAll(input))
        }
    }

    fun input(input: String) {
        _inputValue.postValue(input)
    }
}