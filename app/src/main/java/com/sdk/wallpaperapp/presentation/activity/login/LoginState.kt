package com.sdk.wallpaperapp.presentation.activity.login

import com.sdk.wallpaperapp.domain.model.User

sealed class LoginState {
    object Init: LoginState()
    object Loading: LoginState()
    data class Error(val e: String): LoginState()
    data class Success(val user: User) : LoginState()
}