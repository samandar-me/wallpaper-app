package com.sdk.wallpaperapp.manager

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import com.sdk.wallpaperapp.util.success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyWallpaperManager(
    private val context: Context
) {
    fun setWallpaper(bitmap: Bitmap) {
        CoroutineScope(Dispatchers.IO).launch {
            val wallpaperManager = WallpaperManager.getInstance(context)
            kotlin.runCatching {
                wallpaperManager.setBitmap(bitmap)
            }
            withContext(Dispatchers.Main) {
                context.success("Successfully")
            }
        }
    }
}