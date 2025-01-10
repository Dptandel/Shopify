package com.app.shopify.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shopify.databinding.CategoryContentRvItemBinding
import com.app.shopify.databinding.CategoryContentTitleBinding
import com.app.shopify.models.Category
import com.app.shopify.models.CategoryContent

class CategoryContentAdapter(
    data: List<Pair<Category, List<CategoryContent>>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    private val flatList = mutableListOf<Any>()

    init {
        // Flatten the data into a single list with headers and items
        for ((category, items) in data) {
            flatList.add(category)
            flatList.addAll(items)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (flatList[position] is Category) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val bindingTitle = CategoryContentTitleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ContentTitleViewHolder(bindingTitle)
        } else {
            val binding = CategoryContentRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ContentViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = flatList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentTitleViewHolder) {
            holder.bind(flatList[position] as Category)
        } else if (holder is ContentViewHolder) {
            holder.bind(flatList[position] as CategoryContent)
        }
    }

    fun getPositionForCategory(categoryId: Int): Int {
        return flatList.indexOfFirst { it is Category && it.id == categoryId }
    }

    class ContentTitleViewHolder(val bindingTitle: CategoryContentTitleBinding) :
        RecyclerView.ViewHolder(bindingTitle.root) {
        fun bind(category: Category) {
            bindingTitle.tvContentTitle.text = category.name
        }
    }

    class ContentViewHolder(val binding: CategoryContentRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(content: CategoryContent) {
            binding.tvContentName.text = content.name
            binding.imgContent.setImageResource(content.image)
        }
    }
}