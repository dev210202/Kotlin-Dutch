package jkey20.dutch.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import jkey20.dutch.network.Service
import javax.inject.Inject

class TMapRepository : Repository() {

    suspend fun findAll(input: String, list : MutableLiveData<ArrayList<TMapPOIItem>>): ArrayList<TMapPOIItem>? {

        TMapData().findAllPOI(input) { searchList ->
            searchList.forEach {
                Log.i("LIST", it.name)
            }
            list.value = searchList

        }
        return list.value
    }
}