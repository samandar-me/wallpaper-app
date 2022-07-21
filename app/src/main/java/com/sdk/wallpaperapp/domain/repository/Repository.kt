package com.sdk.wallpaperapp.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.sdk.wallpaperapp.presentation.activity.login.FireBaseSource
import javax.inject.Inject

class Repository @Inject constructor(private val fireBaseSource: FireBaseSource) {

    fun signUpUser(email: String, password: String) = fireBaseSource.signUpUser(email, password)

    fun signInUser(email: String, password: String) = fireBaseSource.signInUser(email, password)

    fun saveUser(email: String, name: String) = fireBaseSource.saveUser(email, name)

    fun fetchUser() = fireBaseSource.fetchUser()
}