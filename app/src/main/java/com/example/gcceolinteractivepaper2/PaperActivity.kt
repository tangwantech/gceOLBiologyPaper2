package com.example.gcceolinteractivepaper2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gcceolinteractivepaper2.databinding.ActivityPaperBinding

class PaperActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaperBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}