package com.sdk.wallpaperapp.presentation.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _signOut: MutableStateFlow<SignOutState> = MutableStateFlow(SignOutState.Loading)
    val singOut: StateFlow<SignOutState> get() = _signOut
    fun signOut() {
        _signOut.value = SignOutState.Loading
        viewModelScope.launch {
            delay(2000)
            firebaseAuth.signOut()
            _signOut.value = SignOutState.Success("Successfully logged out")
        }
    }
}