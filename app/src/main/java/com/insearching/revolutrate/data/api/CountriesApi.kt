package com.insearching.revolutrate.data.api

import com.insearching.revolutrate.data.model.CountryDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CountriesApi {
    
    @GET("all")
    suspend fun fetchCountriesDetails(@Query("fields") fields: String): Response<List<CountryDetails>>
}