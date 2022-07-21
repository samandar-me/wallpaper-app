package com.sdk.wallpaperapp.presentation.activity.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sdk.wallpaperapp.domain.model.User
import javax.inject.Inject

class FireBaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth,private val fireStore: FirebaseFirestore) {

    fun signUpUser(email:String,password:String) = firebaseAuth.createUserWithEmailAndPassword(email,password)

    fun signInUser(email: String,password: String) = firebaseAuth.signInWithEmailAndPassword(email,password)

    fun saveUser(email: String,password:String) = fireStore.collection("users").document(email).set(User(email = email, password = password))

    fun fetchUser() = fireStore.collection("users").get()
}