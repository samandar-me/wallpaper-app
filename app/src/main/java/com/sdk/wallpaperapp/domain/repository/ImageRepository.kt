package com.sdk.wallpaperapp.domain.repository

import com.sdk.wallpaperapp.domain.model.PixelImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun saveImage(image: PixelImage)
    fun getAllImages(): Flow<List<PixelImage>>
    suspend fun deleteImage(image: PixelImage)
    suspend fun deleteAllImages()
}