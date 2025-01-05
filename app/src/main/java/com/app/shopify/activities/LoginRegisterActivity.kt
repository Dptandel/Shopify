package com.app.shopify.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.shopify.databinding.ActivityMainBinding

class LoginRegisterActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}