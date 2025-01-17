package com.app.shopify.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.shopify.R
import com.app.shopify.adapters.ProductImageAdapter
import com.app.shopify.databinding.FragmentProductDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up ViewPager2 adapter
        val adapter = ProductImageAdapter()
        binding.vpProductImages.adapter = adapter

        // Set up TabLayout with ViewPager2 for page indicators
        TabLayoutMediator(binding.tabIndicator, binding.vpProductImages) { _, _ -> }.attach()
    }
}