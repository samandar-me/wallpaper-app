package com.sdk.wallpaperapp.util

import androidx.lifecycle.MutableLiveData
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.data.categories_dto.CategoryImage

object Constants {
    var random: MutableLiveData<Int> = MutableLiveData(1)
    const val CLIENT_ID = "420652581382-47pcvi0karr9palo5e29flnaahtlee29.apps.googleusercontent.com"
    const val BASE_URL = "https://api.pexels.com"
    const val END_POINT = "v1/curated"
    const val API_KEY = "563492ad6f917000010000015f115ffc9ce14ae89c39b5a7ada65365"
    const val SEARCH = "v1/search"
    const val TABLE_NAME = "Image_Table"
    const val DB_NAME = "Image.db"

    fun categoryList(): MutableList<CategoryImage> {
        return mutableListOf(
            CategoryImage(
                "Featured",
                R.drawable.img_featured
            ),
            CategoryImage(
                "Cats",
                R.drawable.img_cat
            ),
            CategoryImage(
                "City",
                R.drawable.img_city
            ),
            CategoryImage(
                "Food",
                R.drawable.img_food
            ),
            CategoryImage(
                "Animal",
                R.drawable.img_animal
            ),
            CategoryImage(
                "Nature",
                R.drawable.img_natural
            ),
            CategoryImage(
                "Dogs",
                R.drawable.img_dog
            ),
            CategoryImage(
                "Book",
                R.drawable.img_book
            ),
            CategoryImage(
                "Mountains",
                R.drawable.img_mounta
            ),
            CategoryImage(
                "Fruits",
                R.drawable.img_fruit
            )
        )
    }
}