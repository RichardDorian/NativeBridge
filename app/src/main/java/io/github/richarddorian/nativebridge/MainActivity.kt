package io.github.richarddorian.nativebridge

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.webkit.WebView
import io.github.richarddorian.nativebridge.apis.OtherInterface
import io.github.richarddorian.nativebridge.databinding.ActivityMainBinding
import io.github.richarddorian.nativebridge.utils.NativeBridgeWebViewClient
import io.github.richarddorian.nativebridge.utils.isNetworkAvailable

class MainActivity : Activity() {
    private val isDebug = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WebView.setWebContentsDebuggingEnabled(isDebug)

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
            if (isDebug) {
                // Load debug mode
                webView.loadUrl("http://192.168.1.62:8080")
            } else {
                val deliveryType = getString(R.string.NB_web_delivery_type)

                if (deliveryType == "online") {
                    if (resources.getBoolean(R.bool.NB_web_delivery_backup) && !isNetworkAvailable(this)) {
                        TODO("Start backup website")
                    } else webView.loadUrl(getString(R.string.NB_web_delivery_url))
                } else if (deliveryType == "bundle") {
                    TODO("Bundle app thing (assets folder probably) + logic")
                }
            }
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