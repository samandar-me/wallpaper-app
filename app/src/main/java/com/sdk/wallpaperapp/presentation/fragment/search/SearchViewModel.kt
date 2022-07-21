package com.sdk.wallpaperapp.presentation.fragment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.wallpaperapp.domain.use_case.remote.SearchImageUseCase
import com.sdk.wallpaperapp.presentation.fragment.images.ImagesApiState
import com.sdk.wallpaperapp.source.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchImageUseCase
) : ViewModel() {
    private val _search: MutableStateFlow<ImagesApiState> = MutableStateFlow(ImagesApiState.Init)
    val search: StateFlow<ImagesApiState> get() = _search

    fun searchImage(query: String) {
        viewModelScope.launch {
            useCase.invoke(query).collect {
                when (it) {
                    is Response.Loading -> _search.value = ImagesApiState.Loading
                    is Response.Error -> _search.value = ImagesApiState.Error(it.message.toString())
                    is Response.Success -> _search.value = ImagesApiState.Success(it.data!!)
                }
            }
        }
    }
}