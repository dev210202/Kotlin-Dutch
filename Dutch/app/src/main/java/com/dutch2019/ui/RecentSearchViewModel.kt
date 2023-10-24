package com.dutch2019.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.dutch2019.model.LocationData
import com.dutch2019.model.MutableListLiveData
import com.dutch2019.repository.DBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentSearchViewModel @Inject constructor(
    private val dataBaseRepository: DBRepository
) : BaseViewModel() {
    private val _recentSearchList = MutableListLiveData<LocationData>()
    val recentSearchList: LiveData<List<LocationData>> get() = _recentSearchList


    fun loadSearchData() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            dataBaseRepository.getSearchData()
        }.onSuccess { list ->
            _recentSearchList.postValue(list)
        }.onFailure { throwable ->
            handleError(throwable)
        }
    }

    fun saveSearchDataIntoDB(data: LocationData) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            dataBaseRepository.insertSearchData(data)
        }.onFailure { throwable ->
            throw throwable
        }
    }


    fun addDBData(data: LocationData) {
        _recentSearchList.value?.let {
            _recentSearchList.value = mutableListOf(data).apply {
                addAll(it)
            }
        }
    }
}