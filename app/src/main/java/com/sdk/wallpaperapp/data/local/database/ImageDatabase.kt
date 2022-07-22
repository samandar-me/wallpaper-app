package com.sdk.wallpaperapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdk.wallpaperapp.data.local.dao.ImageDao
import com.sdk.wallpaperapp.domain.model.PixelImage

@Database(entities = [PixelImage::class], version = 3, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun dao(): ImageDao
}