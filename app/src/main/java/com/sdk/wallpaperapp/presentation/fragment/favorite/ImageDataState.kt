package com.sdk.wallpaperapp.presentation.fragment.favorite

import com.sdk.wallpaperapp.domain.model.PixelImage


sealed class ImageDataState {
    object Init: ImageDataState()
    object Loading: ImageDataState()
    data class Error(val e: String): ImageDataState()
    data class Success(val images: List<PixelImage>): ImageDataState()
}