package com.sdk.wallpaperapp.domain.use_case.remote

import com.sdk.wallpaperapp.domain.model.PixelImage
import com.sdk.wallpaperapp.domain.repository.PixelImageRepository
import com.sdk.wallpaperapp.mappers.toPixelImage
import com.sdk.wallpaperapp.source.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchImageUseCase @Inject constructor(
    private val repository: PixelImageRepository
) {
    operator fun invoke(query: String): Flow<Response<List<PixelImage>>> = flow {
        try {
            emit(Response.Loading())
            val list = repository.searchImages(query).photos.map {
                it.toPixelImage()
            }
            emit(Response.Success(list))
        } catch (e: Exception) {
            emit(Response.Error(e.stackTraceToString()))
        }
    }
}