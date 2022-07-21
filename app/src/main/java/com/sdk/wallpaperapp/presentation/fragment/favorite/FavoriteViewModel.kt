package com.sdk.wallpaperapp.presentation.fragment.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.wallpaperapp.domain.use_case.local.DeleteAllImagesUseCase
import com.sdk.wallpaperapp.domain.use_case.local.GetLocalImageUseCase
import com.sdk.wallpaperapp.source.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getLocalImageUseCase: GetLocalImageUseCase,
    private val deleteAllImagesUseCase: DeleteAllImagesUseCase
): ViewModel() {
    private val _images: MutableStateFlow<ImageDataState> = MutableStateFlow(ImageDataState.Init)
    val images: StateFlow<ImageDataState> get() = _images
    fun getLocalImages() {
        viewModelScope.launch {
            getLocalImageUseCase.invoke().collect {
                when(it) {
                    is Response.Loading ->  _images.value = ImageDataState.Loading
                    is Response.Error ->  _images.value = ImageDataState.Error(it.message!!)
                    is Response.Success -> _images.value = ImageDataState.Success(it.data!!)
                }
            }
        }
    }
    fun deleteAllImages() = viewModelScope.launch {
        deleteAllImagesUseCase.invoke()
    }
}