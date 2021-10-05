package jkey20.dutch.repository

import android.util.Log

class SafeCasterRepository : Repository() {

    suspend fun getSafetyIndex(zipCode : String) : String{
        val data = safeCasterService.searchSafetyIndexByZipCode("20210930", zipCode)
        return data.data[0].contactDensityPercentile
    }

}