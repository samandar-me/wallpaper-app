package com.sdk.wallpaperapp.presentation.fragment.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sdk.wallpaperapp.data.categories_dto.CategoryImage
import com.sdk.wallpaperapp.databinding.CategoriesItemBinding

class CategoriesAdapter(
    private val context: Context
) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    lateinit var onClick: (String) -> Unit

    var categoryList: MutableList<CategoryImage> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoriesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class CategoryViewHolder(private val binding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: CategoryImage) {
            binding.imageView.setBackgroundDrawable(ContextCompat.getDrawable(context, image.image))
            binding.textView.text = image.title

            itemView.setOnClickListener {
                onClick.invoke(image.title)
            }
        }
    }
}