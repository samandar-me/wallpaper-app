package com.sdk.wallpaperapp.presentation.activity.register

import com.sdk.wallpaperapp.domain.model.User

sealed class RegisterState {
    object Init: RegisterState()
    object Loading: RegisterState()
    data class Error(val error: String): RegisterState()
    data class Success(val user: User): RegisterState()
}