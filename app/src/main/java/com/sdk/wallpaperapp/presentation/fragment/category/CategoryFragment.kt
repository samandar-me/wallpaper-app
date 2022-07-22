package com.sdk.wallpaperapp.presentation.fragment.category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.databinding.FragmentCategoryBinding
import com.sdk.wallpaperapp.presentation.fragment.images.ImagesAdapter
import com.sdk.wallpaperapp.presentation.fragment.images.ImagesApiState
import com.sdk.wallpaperapp.source.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category) {
    private val binding by viewBinding { FragmentCategoryBinding.bind(it) }
    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var imagesAdapter: ImagesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        imagesAdapter = ImagesAdapter()
        setupRv()
        val title = arguments?.getString("title") as String
        binding.textTitle.text = title
        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
        collectImages(title)
        imagesAdapter.onClick = {
            val bundle = bundleOf("pixel" to it)
            view.findNavController().navigate(R.id.action_categoryFragment_to_detailCategoryFragment, bundle)
        }
    }

    private fun collectImages(title: String) {
        viewModel.searchImage(query = title)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.search.collect {
                when (it) {
                    is ImagesApiState.Init -> Unit
                    is ImagesApiState.Loading -> {
                        binding.recyclerView.isVisible = false
                        binding.shimmerLayout.isVisible = true
                    }
                    is ImagesApiState.Error -> {
                        binding.shimmerLayout.hideShimmer()
                        binding.recyclerView.isVisible = true
                        binding.shimmerLayout.isVisible = false
                        error(it.e)
                    }
                    is ImagesApiState.Success -> {
                        binding.shimmerLayout.hideShimmer()
                        binding.recyclerView.isVisible = true
                        binding.shimmerLayout.isVisible = false
                        imagesAdapter.submitList(it.images)
                    }
                }
            }
        }
    }

    private fun setupRv() = binding.recyclerView.apply {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = imagesAdapter
    }
}
