package com.sdk.wallpaperapp.presentation.fragment.category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.databinding.FragmentCategoryBinding
import com.sdk.wallpaperapp.source.viewBinding

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private val binding by viewBinding { FragmentCategoryBinding.bind(it) }
    private val viewModel: CategoryViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }
    private fun initViews(view: View) {
        val title = arguments?.getString("title") as String
        binding.textTitle.text = title
        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
        collectImages()
    }

    private fun collectImages() {

    }
}