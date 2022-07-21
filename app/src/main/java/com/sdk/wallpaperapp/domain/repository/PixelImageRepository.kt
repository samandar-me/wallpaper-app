package com.sdk.wallpaperapp.domain.repository

import com.sdk.wallpaperapp.data.imageDTO.PexelImageDTO

interface PixelImageRepository {
    suspend fun getAllImages(page: Int, perPage: Int): PexelImageDTO
    suspend fun searchImages(query: String): PexelImageDTO
}