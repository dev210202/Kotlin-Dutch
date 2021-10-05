package jkey20.dutch.ui.safe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dutch2019.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jkey20.dutch.repository.SafeCasterRepository
import jkey20.dutch.repository.TMapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SafeViewModel @Inject constructor(
    private val safeCasterRepository: SafeCasterRepository,
    private val tMapRepository: TMapRepository
) : BaseViewModel() {

    private val _zipCode = MutableLiveData<String>()
    val zipCode: LiveData<String> get() = _zipCode

    private val _safetyIndex = MutableLiveData<String>()
    val safetyIndex: LiveData<String> get() = _safetyIndex

    fun loadZipCode(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _zipCode.postValue(getZipCode(address))
        }
    }

    fun loadSafetyIndex(zipCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _safetyIndex.postValue(getSafetyIndex(zipCode))
        }
    }

    private suspend fun getSafetyIndex(zipCode: String): String {
        return safeCasterRepository.getSafetyIndex(zipCode)
    }

    private suspend fun getZipCode(address: String): String {
        return tMapRepository.getZipCode(address)
    }
}