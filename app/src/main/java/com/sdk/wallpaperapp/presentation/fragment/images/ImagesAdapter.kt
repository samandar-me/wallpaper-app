package com.sdk.wallpaperapp.presentation.fragment.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sdk.wallpaperapp.databinding.ItemLayoutBinding
import com.sdk.wallpaperapp.domain.model.PixelImage

class ImagesAdapter: ListAdapter<PixelImage, ImagesAdapter.ImagesViewHolder>(DiffCallBack()) {
    lateinit var onClick: (PixelImage) -> Unit
    private class DiffCallBack: DiffUtil.ItemCallback<PixelImage>() {
        override fun areItemsTheSame(oldItem: PixelImage, newItem: PixelImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PixelImage, newItem: PixelImage): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ImagesViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: PixelImage) {
            Glide.with(binding.imageView)
                .load(image.src)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(binding.imageView)

            itemView.setOnClickListener {
                onClick.invoke(image)
            }
        }
    }
}