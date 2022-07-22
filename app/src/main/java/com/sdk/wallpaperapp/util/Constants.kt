package com.sdk.wallpaperapp.util

import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.data.categories_dto.CategoryImage

object Constants {
    const val BASE_URL = "https://api.pexels.com"
    const val END_POINT = "v1/curated"
    const val API_KEY = "YOUR API KEY"
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