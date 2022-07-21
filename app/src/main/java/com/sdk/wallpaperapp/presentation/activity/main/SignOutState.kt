package com.sdk.wallpaperapp.presentation.activity.main

sealed class SignOutState {
    object Loading: SignOutState()
    data class Success(val message: String): SignOutState()
}