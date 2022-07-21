package com.sdk.wallpaperapp.presentation.activity.login

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
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val networkControl: NetworkControl,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _user: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Init)
    val user: StateFlow<LoginState> get() = _user

    fun loginUser(email: String, password: String) {
        when {
            email.isEmpty() && password.isEmpty() -> {
                _user.value = LoginState.Error("Enter email and password")
            }
            networkControl.isConnected() -> {
                _user.value = LoginState.Loading
                firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        if (task.result!!.signInMethods?.size == 0) {
                            _user.value = LoginState.Error("User email doesn't exists")
                        } else {
                            repository.signInUser(email, password).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    repository.fetchUser().addOnCompleteListener { userTask ->
                                        if (userTask.isSuccessful) {
                                            userTask.result?.documents?.forEach {
                                                if (it.data!!["email"] == email) {
                                                    val pass = it.data!!.getValue("password")
                                                    _user.value = LoginState.Success(
                                                        User(
                                                            firebaseAuth.currentUser?.email!!,
                                                            pass.toString()
                                                        )
                                                    )
                                                }
                                            }
                                        } else {
                                            _user.value =
                                                LoginState.Error(userTask.exception.toString())
                                        }
                                    }
                                } else {
                                    _user.value = LoginState.Error(task.exception.toString())
                                }
                            }
                        }
                    } else {
                        _user.value = LoginState.Error("Enter email correctly!")
                    }
                }
            }
            else -> {
                _user.value = LoginState.Error("No internet connection!")
            }
        }
    }
}