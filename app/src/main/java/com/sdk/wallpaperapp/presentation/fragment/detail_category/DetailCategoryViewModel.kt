package com.sdk.wallpaperapp.presentation.fragment.detail_category

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.wallpaperapp.domain.model.PixelImage
import com.sdk.wallpaperapp.domain.use_case.local.DeleteImageUseCase
import com.sdk.wallpaperapp.domain.use_case.local.SaveImageUseCase
import com.sdk.wallpaperapp.util.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class DetailCategoryViewModel @Inject constructor(
    private val saveImageUseCase: SaveImageUseCase,
    private val deleteImageUseCase: DeleteImageUseCase
) : ViewModel() {
    fun saveImage(image: PixelImage) = viewModelScope.launch {
        saveImageUseCase.invoke(image)
    }
    fun deleteImage(image: PixelImage) = viewModelScope.launch {
        deleteImageUseCase.invoke(image)
    }
    fun saveToStorage(context: Context, bitmap: Bitmap) = viewModelScope.launch(Dispatchers.IO) {
        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->

                val contentValues = ContentValues().apply {

                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            withContext(Dispatchers.Main) {
                context.success("Saved to Gallery")
            }
        }
    }
}