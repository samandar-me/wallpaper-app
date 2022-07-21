package com.sdk.wallpaperapp.presentation.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.databinding.FragmentMainBinding
import com.sdk.wallpaperapp.presentation.activity.main.MainActivity
import com.sdk.wallpaperapp.source.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding { FragmentMainBinding.bind(it) }
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNav.menu.getItem(position).isChecked = true
            }
        })
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuImages -> changeItem(0)
                R.id.menuCategories -> changeItem(1)
                R.id.menuSearch -> changeItem(2)
                R.id.menuFavorites -> changeItem(3)
            }
            true
        }
    }

    private fun changeItem(index: Int) {
        binding.viewPager.currentItem = index
    }
}