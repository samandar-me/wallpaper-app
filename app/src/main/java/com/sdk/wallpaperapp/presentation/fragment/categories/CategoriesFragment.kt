package com.sdk.wallpaperapp.presentation.fragment.categories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.databinding.FragmentCategoriesBinding
import com.sdk.wallpaperapp.source.viewBinding
import com.sdk.wallpaperapp.util.Constants

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private val binding by viewBinding { FragmentCategoriesBinding.bind(it) }
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        categoriesAdapter = CategoriesAdapter(requireContext())
        setupRv()
        categoriesAdapter.onClick = {
            val category = bundleOf("title" to it)
            view.findNavController()
                .navigate(R.id.action_mainFragment_to_categoryFragment, category)
        }
        categoriesAdapter.categoryList = Constants.categoryList()
    }

    private fun setupRv() = binding.recyclerView.apply {
        adapter = categoriesAdapter
        layoutManager = GridLayoutManager(requireContext(), 2)
    }
}