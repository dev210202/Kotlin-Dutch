package com.dutch2019.ui.recent

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dutch2019.model.LocationDBData
import com.dutch2019.repository.DBRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    private val dataBaseRepository: DBRepository
) : BaseViewModel() {

    private val _recentLocationList = MutableLiveData<List<LocationDBData>>()
    val recentLocationList: LiveData<List<LocationDBData>> get() = _recentLocationList

    fun getRecentDB(application: Application) {
        viewModelScope.launch {
            var list = reverseList(dataBaseRepository.getRecentData())
            _recentLocationList.value = list
        }
    }

    private fun reverseList(list: List<LocationDBData>): List<LocationDBData> {
        var newList = arrayListOf<LocationDBData>()

        for (i in list.size -1 downTo 0) {
            var listValue = list[i]
            newList.add(list[i])
        }
        return newList
    }
}