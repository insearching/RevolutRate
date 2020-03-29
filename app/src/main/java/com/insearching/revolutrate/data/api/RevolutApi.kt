package com.insearching.revolutrate.data.api

import com.insearching.revolutrate.data.model.DigitalRates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutApi {
    
    @GET("android/latest")
    suspend fun latestRates(@Query("base") base: String): Response<DigitalRates>
}