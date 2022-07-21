package com.sdk.wallpaperapp.presentation.fragment.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.wallpaperapp.domain.use_case.remote.GetAllImageUseCase
import com.sdk.wallpaperapp.source.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val useCase: GetAllImageUseCase
): ViewModel() {
    private val _images: MutableStateFlow<ImagesApiState> = MutableStateFlow(ImagesApiState.Init)
    val images: StateFlow<ImagesApiState> get() = _images

    fun getAllImages(page: Int, perPage: Int) {
        viewModelScope.launch {
            delay(1000)
            useCase.invoke(page, perPage).collect {
                when(it) {
                    is Response.Loading -> {
                        _images.value = ImagesApiState.Loading
                    }
                    is Response.Error -> {
                        _images.value = ImagesApiState.Error(it.message.toString())
                    }
                    is Response.Success -> {
                        _images.value = ImagesApiState.Success(it.data!!)
                    }
                }
            }
        }
    }
}