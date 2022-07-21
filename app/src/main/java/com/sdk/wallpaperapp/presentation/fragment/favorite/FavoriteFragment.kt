package com.sdk.wallpaperapp.presentation.fragment.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.databinding.FragmentFavoriteBinding
import com.sdk.wallpaperapp.domain.model.PixelImage
import com.sdk.wallpaperapp.presentation.fragment.images.ImagesAdapter
import com.sdk.wallpaperapp.source.viewBinding
import com.sdk.wallpaperapp.util.error
import com.sdk.wallpaperapp.util.success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding { FragmentFavoriteBinding.bind(it) }
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var imagesAdapter: ImagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        imagesAdapter = ImagesAdapter()
        setupRv()
        imagesAdapter.onClick = {
            val image = PixelImage(
                it.id,
                it.url,
                it.photographer,
                it.photographer_url,
                it.src,
                it.src,
                true
            )
            val bundle = bundleOf("pixel" to image)
            view.findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
        collect()
        binding.btnFab.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.de))
            setMessage(getString(R.string.del_all))
            setPositiveButton("Yes") { dialog, _ ->
                viewModel.deleteAllImages()
                success(getString(R.string.all_del))
                dialog.dismiss()
            }
            setNeutralButton("No", null)
        }.create().show()
    }

    private fun collect() {
        viewModel.getLocalImages()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.images.collect {
                when (it) {
                    is ImageDataState.Init -> Unit
                    is ImageDataState.Loading -> Unit
                    is ImageDataState.Error -> error(it.e)
                    is ImageDataState.Success -> {
                        imagesAdapter.submitList(it.images)
                        binding.btnFab.isVisible = it.images.isNotEmpty()
                        binding.textView.isVisible = it.images.isEmpty()
                    }
                }
            }
        }
    }

    private fun setupRv() = binding.recyclerView.apply {
        adapter = imagesAdapter
        layoutManager = GridLayoutManager(requireContext(), 2)
    }
}