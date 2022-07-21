package com.sdk.wallpaperapp.data.repository

import com.sdk.wallpaperapp.data.imageDTO.PexelImageDTO
import com.sdk.wallpaperapp.data.remote.ApiService
import com.sdk.wallpaperapp.domain.repository.PixelImageRepository
import javax.inject.Inject

class PixelImageRepositoryImpl @Inject constructor(private val apiService: ApiService):
    PixelImageRepository {
    override suspend fun getAllImages(page: Int, perPage: Int): PexelImageDTO {
        return apiService.getAllImages(page, perPage)
    }

    override suspend fun searchImages(query: String): PexelImageDTO {
        return apiService.searchImage(query)
    }
}