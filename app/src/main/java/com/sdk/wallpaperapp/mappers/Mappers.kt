package com.sdk.wallpaperapp.mappers

import com.sdk.wallpaperapp.data.imageDTO.Photo
import com.sdk.wallpaperapp.domain.model.PixelImage

fun Photo.toPixelImage(): PixelImage {
    return PixelImage(
        id = id,
        url = url,
        photographer = photographer,
        photographer_url = photographer_url,
        src = src.medium,
        portrait = src.portrait
    )
}
