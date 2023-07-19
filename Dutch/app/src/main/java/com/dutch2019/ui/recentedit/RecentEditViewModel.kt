package com.dutch2019.ui.recentedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dutch2019.model.LocationSearchData
import com.dutch2019.repository.DBRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentEditViewModel @Inject constructor(
    private val dataBaseRepository: DBRepository
) : BaseViewModel() {
    private val _recentLocationList = MutableLiveData<List<LocationSearchData>>()
    val recentLocationList: LiveData<List<LocationSearchData>> get() = _recentLocationList

    fun getRecentDB() {
        viewModelScope.launch {
            var list = reverseList(dataBaseRepository.getRecentData())
            _recentLocationList.value = list
        }
    }

    fun deleteRecentDB(list : List<LocationSearchData>){
        viewModelScope.launch {
            dataBaseRepository.deleteRecentData(list)
            getRecentDB()
        }
    }

    private fun reverseList(list: List<LocationSearchData>): List<LocationSearchData> {
        var newList = arrayListOf<LocationSearchData>()

        for (i in list.size -1 downTo 0) {
            var listValue = list[i]
            newList.add(list[i])
        }
        return newList
    }
}