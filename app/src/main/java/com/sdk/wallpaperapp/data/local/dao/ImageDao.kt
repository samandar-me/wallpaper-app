package com.sdk.wallpaperapp.data.local.dao

import androidx.room.*
import com.sdk.wallpaperapp.domain.model.PixelImage
import com.sdk.wallpaperapp.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveImage(image: PixelImage)

    @Query("SELECT * FROM Image_Table ORDER BY id DESC")
    fun getAllImages(): Flow<List<PixelImage>>

    @Delete
    suspend fun deleteImage(image: PixelImage)

    @Query("DELETE FROM ${Constants.TABLE_NAME} ")
    suspend fun deleteAllImages()
}