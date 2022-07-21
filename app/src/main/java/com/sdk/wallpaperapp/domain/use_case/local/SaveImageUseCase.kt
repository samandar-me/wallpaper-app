package com.sdk.wallpaperapp.domain.use_case.local

import com.sdk.wallpaperapp.domain.model.PixelImage
import com.sdk.wallpaperapp.domain.repository.ImageRepository
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(image: PixelImage) {
        imageRepository.saveImage(image)
    }
}