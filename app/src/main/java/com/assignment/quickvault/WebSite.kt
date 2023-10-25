package com.assignment.quickvault

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView

class WebSite : AppCompatActivity() {
    lateinit var webViewDynamic: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_site)

        webViewDynamic = findViewById(R.id.webViewDynamic)

        val url = "quickvault.netlify.app"

        val webSettings: WebSettings = webViewDynamic.settings

        webSettings.javaScriptEnabled = true

        webViewDynamic.loadUrl(url)
    }
}