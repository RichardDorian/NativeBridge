package io.github.richarddorian.nativebridge.apis

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.webkit.JavascriptInterface
import android.webkit.WebView
import io.github.richarddorian.nativebridge.utils.addJavaScriptAPI

class OtherInterface(webView: WebView, private val activity: Activity) {
    init {
        webView.addJavaScriptAPI(this, "Other")
    }

    @JavascriptInterface
    fun getSdkNumber(): String {
        return Build.VERSION.SDK_INT.toString()
    }

    @SuppressLint("DiscouragedApi")
    @JavascriptInterface
    fun getString(resourceName: String): String? {
        val identifier =
            activity.resources.getIdentifier(resourceName, "string", activity.packageName)
        if (identifier == 0) return null
        return activity.getString(identifier)
    }
}