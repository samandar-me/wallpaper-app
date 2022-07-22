package com.sdk.wallpaperapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdk.wallpaperapp.util.Constants
import kotlinx.parcelize.Parcelize

@Entity(tableName = Constants.TABLE_NAME)
@Parcelize
data class PixelImage(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val portrait: String,
    val src: String,
    val image: String,
    val isSaved: Boolean = false
): Parcelable