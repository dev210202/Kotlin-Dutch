package jkey20.dutch.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import dagger.hilt.android.lifecycle.HiltViewModel
import jkey20.dutch.repository.TMapRepository
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
//            if (input.isNotEmpty()) {
//                TMapData().findAllPOI(input) { searchList ->
//                    _locationList.postValue(searchList)
//                }
//            } else {
//                toastValue.postValue("검색할 위치를 입력해주세요!")
//            }
            tMapRepository.findAll(input, _locationList)
        }
    }

    fun input(input: String) {
        _inputValue.postValue(input)
    }
}