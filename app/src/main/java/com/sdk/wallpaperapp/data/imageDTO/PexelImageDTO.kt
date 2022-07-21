package com.sdk.wallpaperapp.data.imageDTO

data class PexelImageDTO(
    val next_page: String?,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int
)