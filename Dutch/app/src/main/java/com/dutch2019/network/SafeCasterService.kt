package com.dutch2019.network

import com.dutch2019.BuildConfig
import com.dutch2019.model.SafetyIndexDataList
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SafeCasterService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json; charset=UTF-8",
        "appKey: ${BuildConfig.T_SAFETY_CASTER_API}"
    )
    @GET("safecaster/v1/search/safetyindex/zip/overall")
    suspend fun searchSafetyIndexByZipCode(
        @Query("filterDate") filterDate: String,
        @Query("zipCd") zipCd: String
    ): SafetyIndexDataList
}