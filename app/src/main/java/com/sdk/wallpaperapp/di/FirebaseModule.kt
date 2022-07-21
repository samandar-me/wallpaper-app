package com.sdk.wallpaperapp.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sdk.wallpaperapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFireBaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideGso(@ApplicationContext context: Context) = GoogleSignInOptions.Builder(
        GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.client_id))
        .requestEmail()
        .build()

    @Provides
    @Singleton
    fun provideGoogleClient(@ApplicationContext context: Context, gso: GoogleSignInOptions) = GoogleSignIn.getClient(context, gso)

    @Provides
    @Singleton
    fun provideFirestore()= FirebaseFirestore.getInstance()
}