package com.example.gcceolinteractivepaper2

import android.content.res.AssetManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gcceolinteractivepaper2.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWebView()

    }

    private fun setupWebView(){
        binding.webView.loadUrl(AssertFileReader.getAssetFilePath("index.html"))
    }
}