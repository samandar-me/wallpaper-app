package com.sdk.wallpaperapp.di

import android.content.Context
import androidx.room.Room
import com.sdk.wallpaperapp.data.local.dao.ImageDao
import com.sdk.wallpaperapp.data.local.database.ImageDatabase
import com.sdk.wallpaperapp.data.repository.ImageRepositoryImpl
import com.sdk.wallpaperapp.domain.repository.ImageRepository
import com.sdk.wallpaperapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ImageDatabase {
        return Room.databaseBuilder(
            context,
            ImageDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideImageDao(imageDatabase: ImageDatabase): ImageDao = imageDatabase.dao()

    @Provides
    @Singleton
    fun provideImageRepository(imageDao: ImageDao): ImageRepository {
        return ImageRepositoryImpl(imageDao)
    }
}