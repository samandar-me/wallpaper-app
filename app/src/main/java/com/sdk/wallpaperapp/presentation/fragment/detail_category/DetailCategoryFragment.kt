package com.sdk.wallpaperapp.presentation.fragment.detail_category

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sdk.wallpaperapp.R
import com.sdk.wallpaperapp.databinding.FragmentDetailCategoryBinding
import com.sdk.wallpaperapp.domain.model.PixelImage
import com.sdk.wallpaperapp.manager.MyWallpaperManager
import com.sdk.wallpaperapp.presentation.activity.main.MainActivity
import com.sdk.wallpaperapp.presentation.fragment.detail.DetailViewModel
import com.sdk.wallpaperapp.source.viewBinding
import com.sdk.wallpaperapp.util.success
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailCategoryFragment : Fragment(R.layout.fragment_detail_category) {
    private val binding by viewBinding { FragmentDetailCategoryBinding.bind(it) }
    private val viewModel: DetailCategoryViewModel by viewModels()
    private lateinit var myWallpaperManager: MyWallpaperManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        val pixelImage = arguments?.get("pixel") as PixelImage
        myWallpaperManager = MyWallpaperManager(requireContext())
        binding.textName.text = pixelImage.photographer
        Glide.with(binding.imageView)
            .load(pixelImage.portrait)
            .into(binding.imageView)
        binding.floatingActionButton.setOnClickListener {
            showBottomSheet(pixelImage, view)
        }
    }

    private fun showBottomSheet(pixelImage: PixelImage, v: View) {
        val view: View =
            (activity as MainActivity).layoutInflater.inflate(R.layout.bottom_sheet, null)
        val linearFav: LinearLayout = view.findViewById(R.id.linearFav)
        val linearGal: LinearLayout = view.findViewById(R.id.linearGal)
        val textView: AppCompatTextView = view.findViewById(R.id.textDelFav)
        val imageView: AppCompatImageView = view.findViewById(R.id.imageDelFav)
        val textLink: AppCompatTextView = view.findViewById(R.id.textLink)
        val linearWall: LinearLayout = view.findViewById(R.id.linearWall)
        textLink.text = pixelImage.url
        val bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.dismissWithAnimation = true
        linearWall.setOnClickListener {
            val bitmap: Bitmap = binding.imageView.drawable.toBitmap()
            myWallpaperManager.setWallpaper(bitmap)
            bottomSheet.dismiss()
        }
        if (pixelImage.isSaved) {
            textView.text = getString(R.string.delete)
            imageView.setBackgroundResource(R.drawable.ic_baseline_delete_outline_24)
        } else {
            imageView.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }
        linearFav.setOnClickListener {
            if (pixelImage.isSaved) {
                viewModel.deleteImage(pixelImage)
                bottomSheet.dismiss()
                success(getString(R.string.del))
                v.findNavController().popBackStack()
            } else {
                viewModel.saveImage(pixelImage)
                bottomSheet.dismiss()
                success(getString(R.string.saved))
            }
        }
        linearGal.setOnClickListener {
            saveToGallery()
            bottomSheet.dismiss()
        }
        bottomSheet.setContentView(view)
        bottomSheet.show()
    }

    private fun saveToGallery() {
        val bitmap = getScreenShotFromView(binding.imageView)
        if (bitmap != null) {
            viewModel.saveToStorage(requireContext(),bitmap)
        }
    }

    private fun getScreenShotFromView(imageView: AppCompatImageView): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            bitmap = Bitmap.createBitmap(
                imageView.measuredWidth,
                imageView.measuredHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            imageView.draw(canvas)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.stackTraceToString(), Toast.LENGTH_SHORT).show()
        }
        return bitmap
    }
}