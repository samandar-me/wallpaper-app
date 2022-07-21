package com.sdk.wallpaperapp.presentation.fragment.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.databinding.FragmentSearchBinding
import com.sdk.wallpaperapp.presentation.fragment.images.ImagesAdapter
import com.sdk.wallpaperapp.presentation.fragment.images.ImagesApiState
import com.sdk.wallpaperapp.source.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding { FragmentSearchBinding.bind(it) }
    private val viewModel: SearchViewModel by viewModels()
    private var clickListener: LogOutClickListener? = null
    private lateinit var imagesAdapter: ImagesAdapter
    private var job: Job? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        imagesAdapter = ImagesAdapter()
        setupRv()
        binding.btnLogOut.setOnClickListener {
            clickListener?.onClick()
        }
        binding.editText.addTextChangedListener {
            val query = it.toString().trim()
            searchImage(query)
            if (query.isNotEmpty()) {
                binding.btnClose.visibility = View.VISIBLE
            } else {
                binding.btnClose.visibility = View.INVISIBLE
            }
        }
        binding.btnClose.setOnClickListener {
            binding.editText.text?.clear()
        }
        imagesAdapter.onClick = {
            val bundle = bundleOf("pixel" to it)
            view.findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    private fun setupRv() = binding.recyclerView.apply {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = imagesAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clickListener = if (context is LogOutClickListener) {
            context
        } else {
            throw RuntimeException("$context must implement FirsListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    interface LogOutClickListener {
        fun onClick()
    }

    private fun searchImage(text: String?) {
        job?.cancel()
        job = MainScope().launch {
            delay(700L)
            text?.let { query ->
                if (query.isNotEmpty()) {
                    viewModel.searchImage(query)
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.search.collect {
                            when (it) {
                                is ImagesApiState.Init -> Unit
                                is ImagesApiState.Loading -> {
                                    binding.textView.isVisible = false
                                    binding.shimmerLayout.isVisible = true
                                    binding.recyclerView.isVisible = false
                                }
                                is ImagesApiState.Error -> {
                                    binding.textView.isVisible = true
                                    binding.shimmerLayout.isVisible = false
                                    binding.recyclerView.isVisible = false
                                    error(it.e)
                                }
                                is ImagesApiState.Success -> {
                                    binding.textView.isVisible = false
                                    binding.shimmerLayout.isVisible = false
                                    binding.recyclerView.isVisible = true
                                    imagesAdapter.submitList(it.images)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}