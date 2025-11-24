package com.example.englishwithchristian

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        // 1) Hook the toolbar as Action Bar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val sb = supportActionBar
        Log.d("WEB", "supportActionBar == null? ${sb == null}")

        // 2) Force-show back arrow
        sb?.apply {
            title = "Go back"
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        // 3) If theme hides the default icon, force a navigation icon + tint
        if (toolbar.navigationIcon == null) {
            val nav = AppCompatResources.getDrawable(
                this,
                androidx.appcompat.R.drawable.abc_ic_ab_back_material
            )
            // If your bar is dark, tint white; if your bar is light, tint black:
            nav?.setTint(ContextCompat.getColor(this, android.R.color.white))
            toolbar.navigationIcon = nav
        }
        toolbar.setNavigationOnClickListener {
            if (this::webView.isInitialized && webView.canGoBack()) {
                webView.goBack()
            } else {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // 4) WebView setup
        webView = findViewById(R.id.webView)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: android.webkit.WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

        intent.getStringExtra("url")?.let { webView.loadUrl(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (this::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
        return true
    }

    @Deprecated("Deprecated but fine for compatibility")
    override fun onBackPressed() {
        if (this::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}