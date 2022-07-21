package com.sdk.wallpaperapp.presentation.activity.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sdk.wallpaperapp.domain.model.User
import com.sdk.wallpaperapp.domain.repository.Repository
import com.sdk.wallpaperapp.util.NetworkControl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository,
    private val networkControl: NetworkControl,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _user: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Init)
    val user: StateFlow<RegisterState> get() = _user
    private val _saveUser: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Init)
    val saveUser: StateFlow<RegisterState> get() = _saveUser

    fun registerUser(name: String, email: String, password: String) {
        when {
            name.isEmpty() && email.isEmpty() && password.isEmpty() -> {
                _user.value = RegisterState.Error("Field must be not empty")
            }
            password.length < 6 -> {
                _user.value = RegisterState.Error("Password must not be less than 6")
            }
            networkControl.isConnected() -> {
                _user.value = RegisterState.Loading
                firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        if (it.result.signInMethods?.size == 0) {
                            repository.signUpUser(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        firebaseAuth.currentUser?.sendEmailVerification()
                                        _user.value = RegisterState.Success(User(email, password))
                                    } else {
                                        _user.value =
                                            RegisterState.Error(task.exception!!.message.toString())
                                    }
                                }
                        } else {
                            _user.value = RegisterState.Error("Email already exists")
                        }
                    } else {
                        _user.value = RegisterState.Error("Enter email correctly")
                    }
                }
            }
            else -> {
                _user.value = RegisterState.Error("No internet connection")
            }
        }
    }

    fun saveUser(email: String, password: String) {
        repository.saveUser(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _saveUser.value = RegisterState.Success(User(email, password))
            } else {
                _saveUser.value = RegisterState.Error(it.exception!!.message.toString())
            }
        }
    }
}