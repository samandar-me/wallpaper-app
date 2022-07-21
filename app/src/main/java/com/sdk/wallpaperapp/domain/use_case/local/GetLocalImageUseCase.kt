package com.sdk.wallpaperapp.domain.use_case.local

import android.util.Log
import com.sdk.wallpaperapp.domain.model.PixelImage
import com.sdk.wallpaperapp.domain.repository.ImageRepository
import com.sdk.wallpaperapp.source.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetLocalImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    operator fun invoke(): Flow<Response<List<PixelImage>>> = flow {
        try {
            emit(Response.Loading())
            repository.getAllImages().collect {
                emit(Response.Success(it))
            }

        } catch (e: Exception) {
            emit(Response.Error(e.message))
        }
    }
}