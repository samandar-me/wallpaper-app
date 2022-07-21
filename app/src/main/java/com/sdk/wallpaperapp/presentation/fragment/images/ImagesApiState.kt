package com.sdk.wallpaperapp.presentation.fragment.images

import com.sdk.wallpaperapp.domain.model.PixelImage

sealed class ImagesApiState {
    object Init: ImagesApiState()
    object Loading: ImagesApiState()
    data class Error(val e: String): ImagesApiState()
    data class Success(val images: List<PixelImage>): ImagesApiState()
}