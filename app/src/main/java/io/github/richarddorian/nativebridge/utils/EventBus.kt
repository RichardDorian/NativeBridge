package io.github.richarddorian.nativebridge.utils

import android.webkit.WebView
import org.json.JSONArray

fun WebView.emit(event: String, vararg args: Any) {
    val argsArray = JSONArray()
    args.forEach { argsArray.put(it) }

    this.post {
        this.evaluateJavascript("Android?.Events?.emit('$event',$argsArray);") {}
    }
}