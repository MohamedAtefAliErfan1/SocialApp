package com.mohamedabdelaziz.socialapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mohamedabdelaziz.socialapp.R
import dagger.hilt.android.AndroidEntryPoint
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.webkit.WebView
import android.webkit.WebResourceRequest

import android.webkit.WebViewClient

@AndroidEntryPoint
class CustomViewer : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_viewer)
        val myWebView:WebView = findViewById(R.id.webView)
        myWebView.loadUrl(intent.extras?.getString("url").toString());
        myWebView.setBackgroundColor(Color.TRANSPARENT);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                return false
            }
        }
    }
}