package com.app.shopify.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopify.R
import com.app.shopify.adapters.CategoryAdapter
import com.app.shopify.adapters.CategoryContentAdapter
import com.app.shopify.databinding.FragmentCategoryBinding
import com.app.shopify.models.Category
import com.app.shopify.models.CategoryContent

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private lateinit var binding: FragmentCategoryBinding

    private val categories = listOf(
        Category(1, R.drawable.ic_launcher_background, "Popular"),
        Category(2, R.drawable.ic_launcher_background, "Kurti, Saree & Lehenga"),
        Category(3, R.drawable.ic_launcher_background, "Women Western"),
        Category(4, R.drawable.ic_launcher_background, "Men"),
        Category(5, R.drawable.ic_launcher_background, "Kids & Toys"),
        Category(6, R.drawable.ic_launcher_background, "Home & Kitchen"),
        Category(7, R.drawable.ic_launcher_background, "Beauty & Health"),
        Category(8, R.drawable.ic_launcher_background, "Jewellery & Accessories"),
        Category(9, R.drawable.ic_launcher_background, "Bags & Footwear"),
        Category(10, R.drawable.ic_launcher_background, "Electronics"),
        Category(11, R.drawable.ic_launcher_background, "Watches"),
        Category(12, R.drawable.ic_launcher_background, "Sports & Fitness"),
        Category(13, R.drawable.ic_launcher_background, "Car & Motorbike"),
        Category(14, R.drawable.ic_launcher_background, "Office Supplies & Stationery"),
        Category(15, R.drawable.ic_launcher_background, "Grocery"),
        Category(16, R.drawable.ic_launcher_background, "Books"),
        Category(17, R.drawable.ic_launcher_background, "Pet Supplies"),
        Category(18, R.drawable.ic_launcher_background, "Musical Instruments")
    )

    private val contents = listOf(
        CategoryContent(1, R.drawable.ic_launcher_background, "Top Brands"),
        CategoryContent(1, R.drawable.ic_launcher_background, "Premium Collection"),
        CategoryContent(2, R.drawable.ic_launcher_background, "All Sarees"),
        CategoryContent(2, R.drawable.ic_launcher_background, "Georgette Sarees"),
        CategoryContent(2, R.drawable.ic_launcher_background, "Chiffon Sarees"),
        CategoryContent(2, R.drawable.ic_launcher_background, "Cotton Sarees"),
        CategoryContent(2, R.drawable.ic_launcher_background, "Net Sarees"),
        CategoryContent(2, R.drawable.ic_launcher_background, "Under 299"),
        CategoryContent(2, R.drawable.ic_launcher_background, "Silk Sarees"),
        CategoryContent(2, R.drawable.ic_launcher_background, "New Collection"),
        CategoryContent(2, R.drawable.ic_launcher_background, "Bridal Sarees")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Group items by category
        val groupedData = categories.map { category ->
            category to contents.filter { it.categoryId == category.id }
        }

        // set up the recycler view of Category
        binding.rvCategory.layoutManager = LinearLayoutManager(context)
        val categoryAdapter = CategoryAdapter(categories) { categoryId ->
            scrollToCategory(categoryId)
        }
        binding.rvCategory.adapter = categoryAdapter

        // Contents of Category
        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when ((binding.rvCategoryContent.adapter as CategoryContentAdapter).getItemViewType(
                    position
                )) {
                    CategoryContentAdapter.VIEW_TYPE_HEADER -> 3 // Header takes full row
                    CategoryContentAdapter.VIEW_TYPE_ITEM -> 1 // Item takes one column
                    else -> 1
                }
            }

        }
        binding.rvCategoryContent.layoutManager = layoutManager
        binding.rvCategoryContent.adapter = CategoryContentAdapter(groupedData)
    }

    private fun scrollToCategory(categoryId: Int) {
        val contentAdapter = binding.rvCategoryContent.adapter as? CategoryContentAdapter
        val position = contentAdapter?.getPositionForCategory(categoryId)
        if (position != null) {
            binding.rvCategoryContent.layoutManager?.let {
                (it as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
            }
        }
    }
}