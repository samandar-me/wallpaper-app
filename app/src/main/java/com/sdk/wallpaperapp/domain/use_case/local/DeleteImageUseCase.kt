package com.sdk.wallpaperapp.domain.use_case.local

import com.sdk.wallpaperapp.domain.model.PixelImage
import com.sdk.wallpaperapp.domain.repository.ImageRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(image: PixelImage) {
        repository.deleteImage(image)
    }
}