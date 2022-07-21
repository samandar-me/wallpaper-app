package com.sdk.wallpaperapp.data.repository

import com.sdk.wallpaperapp.data.local.dao.ImageDao
import com.sdk.wallpaperapp.domain.model.PixelImage
import com.sdk.wallpaperapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao
) : ImageRepository {
    override suspend fun saveImage(image: PixelImage) {
        imageDao.saveImage(image)
    }

    override fun getAllImages(): Flow<List<PixelImage>> {
        return imageDao.getAllImages()
    }

    override suspend fun deleteImage(image: PixelImage) {
        imageDao.deleteImage(image)
    }

    override suspend fun deleteAllImages() {
        imageDao.deleteAllImages()
    }
}