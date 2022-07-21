package com.sdk.wallpaperapp.presentation.fragment.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sdk.wallpaperapp.presentation.fragment.categories.CategoriesFragment
import com.sdk.wallpaperapp.presentation.fragment.favorite.FavoriteFragment
import com.sdk.wallpaperapp.presentation.fragment.images.ImagesFragment
import com.sdk.wallpaperapp.presentation.fragment.search.SearchFragment

class ViewPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ImagesFragment()
            1 -> CategoriesFragment()
            2 -> SearchFragment()
            3 -> FavoriteFragment()
            else -> Fragment()
        }
    }
}