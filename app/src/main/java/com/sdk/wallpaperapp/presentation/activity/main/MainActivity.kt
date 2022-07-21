package com.sdk.wallpaperapp.presentation.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.databinding.ActivityMainBinding
import com.sdk.wallpaperapp.presentation.fragment.search.SearchFragment
import com.sdk.wallpaperapp.util.Constants
import com.sdk.wallpaperapp.util.success
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchFragment.LogOutClickListener {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var view: View
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constants.random.value = random()
    }

    override fun onClick() {
        showDialog()
    }

    private fun showDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.sign_out))
            setMessage(getString(R.string.are_you))
            setIcon(R.drawable.ic_baseline_error_outline_24)
            setNegativeButton("No", null)
            setPositiveButton("Yes") { _, _ ->
                viewModel.signOut()
                lifecycleScope.launch {
                    viewModel.singOut.collect {
                        when (it) {
                            is SignOutState.Loading -> {
                                binding.progressBar.isVisible = true
                            }
                            is SignOutState.Success -> {
                                success(getString(R.string.succ))
                                finish()
                            }
                        }
                    }
                }
            }
        }.create().show()
    }
    private fun random(): Int {
        val random = Random()
        return random.nextInt(8)
    }
}