package com.sdk.wallpaperapp.data.remote

import com.sdk.wallpaperapp.data.imageDTO.PexelImageDTO
import com.sdk.wallpaperapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers(
        "Authorization: ${Constants.API_KEY}"
    )
    @GET(Constants.END_POINT)
    suspend fun getAllImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PexelImageDTO

    @Headers(
        "Authorization: ${Constants.API_KEY}"
    )
    @GET(Constants.SEARCH)
    suspend fun searchImage(
        @Query("query") query: String
    ): PexelImageDTO
}