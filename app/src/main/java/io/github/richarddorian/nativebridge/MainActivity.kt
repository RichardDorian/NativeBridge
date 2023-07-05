package io.github.richarddorian.nativebridge

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.webkit.WebView
import io.github.richarddorian.nativebridge.apis.OtherInterface
import io.github.richarddorian.nativebridge.databinding.ActivityMainBinding
import io.github.richarddorian.nativebridge.utils.NativeBridgeWebViewClient

class MainActivity : Activity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WebView.setWebContentsDebuggingEnabled(0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)

        webView = findViewById(R.id.webview_root)

        webView.webViewClient = NativeBridgeWebViewClient(assets)
        webView.settings.apply {
            allowFileAccess = false
            javaScriptEnabled = true
        }

        // [API_LIST_START] # Do not edit this line!

        OtherInterface(webView, this)

        // [API_LIST_END] # Do not edit this line!

        if (savedInstanceState == null) {
            webView.loadUrl("http:///192.168.0.163:8080/")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView.restoreState(savedInstanceState)
    }
}