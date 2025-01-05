package com.app.shopify.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.shopify.databinding.ActivityLoginRegisterBinding

class LoginRegisterActivity : AppCompatActivity() {

    private val binding: ActivityLoginRegisterBinding by lazy {
        ActivityLoginRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}