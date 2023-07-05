package io.github.richarddorian.nativebridge.utils

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient

@SuppressLint("JavascriptInterface")
fun WebView.addJavaScriptAPI(api: Any, name: String) =
    this.addJavascriptInterface(api, "Android_$name")

class NativeBridgeWebViewClient(private val assets: AssetManager) : WebViewClient() {
    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        if (request?.url?.toString() == "native-bridge://api.js") {
            return WebResourceResponse(
                "text/javascript",
                "utf-8",
                200,
                "OK",
                mapOf(
                    "Cache-Control" to "no-store"
                ),
                assets.open("api.js"),
            )
        }

        return super.shouldInterceptRequest(view, request)
    }
}