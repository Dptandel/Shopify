package com.app.shopify.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopify.databinding.BestDealsRvItemBinding
import com.app.shopify.models.Product
import com.bumptech.glide.Glide
import kotlin.math.round

class BestDealsAdapter : RecyclerView.Adapter<BestDealsAdapter.BestDealsViewHolder>() {
    inner class BestDealsViewHolder(
        private val binding: BestDealsRvItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.apply {
                Glide.with(itemView).load(product.images[0]).into(imgBestDeal)
                tvBestDealProductName.text = product.name

                val discount = product.offerPercentage!! / 100
                val priceAfterDiscount =
                    round(product.price - (discount * product.price)).toInt().toString()
                val originalPrice = product.price.toInt().toString()

                tvNewPrice.text = "₹ $priceAfterDiscount"
                tvOldPrice.text = "₹ $originalPrice"
                tvOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealsViewHolder {
        return BestDealsViewHolder(
            BestDealsRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: BestDealsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }
}