package jkey20.dutch.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPOIItem
import jkey20.dutch.network.Service
import javax.inject.Inject

class TMapRepository : Repository() {

    suspend fun findAll(input: String): ArrayList<TMapPOIItem>? {
        return TMapData().findAllPOI(input)
    }
}