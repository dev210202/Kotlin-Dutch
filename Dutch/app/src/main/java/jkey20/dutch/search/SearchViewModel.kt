package jkey20.dutch.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    private val _locationList = MutableLiveData<ArrayList<TMapPOIItem>>(ArrayList())
    val locationList: LiveData<ArrayList<TMapPOIItem>> get() = _locationList

    private val _inputValue = MutableLiveData<String>()
    val inputValue: LiveData<String> get() = _inputValue

    fun search(input: String) {
        viewModelScope.launch {
            if (input.isNotEmpty()) {
                TMapData().findAllPOI(input) { searchList ->
                    _locationList.postValue(searchList)
                }
            } else {
                toastValue.postValue("검색할 위치를 입력해주세요!")
            }
        }
    }

    fun input(input: String) {
        _inputValue.postValue(input)
    }
}