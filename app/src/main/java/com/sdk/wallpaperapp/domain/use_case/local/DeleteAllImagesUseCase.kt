package com.sdk.wallpaperapp.domain.use_case.local

import com.sdk.wallpaperapp.domain.repository.ImageRepository
import javax.inject.Inject

class DeleteAllImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllImages()
    }
}