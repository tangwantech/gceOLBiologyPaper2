package com.example.gcceolinteractivepaper2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gcceolinteractivepaper2.databinding.ActivityTermsOfServiceBinding
import com.example.gcceolinteractivepaper2.databinding.TermsOfUseLayoutBinding

class TermsOfServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermsOfServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsOfServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.terms_of_service)
        initViews()
    }
    private fun initViews(){
        binding.webView.loadUrl(AppConstants.TERMS_URL)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    companion object{
        fun getIntent(context: Context): Intent {
            return Intent(context, TermsOfServiceActivity::class.java)
        }
    }
}