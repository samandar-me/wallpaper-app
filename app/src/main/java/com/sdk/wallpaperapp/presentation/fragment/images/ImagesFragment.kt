package com.sdk.wallpaperapp.presentation.fragment.images

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.databinding.FragmentImagesBinding
import com.sdk.wallpaperapp.source.viewBinding
import com.sdk.wallpaperapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class ImagesFragment : Fragment(R.layout.fragment_images) {

    private lateinit var imagesAdapter: ImagesAdapter
    private val binding by viewBinding { FragmentImagesBinding.bind(it) }
    private val viewModel: ImagesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        binding.shimmerLayout.startShimmer()
        imagesAdapter = ImagesAdapter()
        setupRv()
        imagesAdapter.onClick = {
            val bundle = bundleOf("pixel" to it)
            view.findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
        Constants.random.observe(viewLifecycleOwner) {
            collectImage(it)
        }
    }

    private fun collectImage(random: Int) {
        viewModel.getAllImages(random, 100)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.images.collect {
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