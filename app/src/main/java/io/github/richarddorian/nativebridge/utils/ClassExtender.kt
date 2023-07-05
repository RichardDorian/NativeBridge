package io.github.richarddorian.nativebridge.utils

import android.webkit.JavascriptInterface
import org.json.JSONArray
import org.json.JSONTokener
import java.util.UUID

open class ClassExtender<T : Any>(private val builder: () -> T) {
    private val instances = mutableMapOf<String, T>()
    private lateinit var jsInterface: Any

    @JavascriptInterface
    fun instantiate(): String {
        val id = UUID.randomUUID().toString()
        instances[id] = builder()
        return id
    }

    @JavascriptInterface
    fun call(id: String?, method: String, rawArguments: String?): Int {
        // We assume we never have multiple methods with the same name
        val declaredMethod =
            jsInterface.javaClass.declaredMethods.find { it.name == method } ?: return 1
        val isInstanceMethod = declaredMethod.isAnnotationPresent(InstanceMethod::class.java)

        var arguments = arrayOf<Any>()
        if (rawArguments != null) {
            val parsed = JSONArray(JSONTokener(rawArguments))
            arguments = Array(parsed.length(), parsed::get)
        }

        if (isInstanceMethod) {
            val instance = instances[id ?: return 2] ?: return 3
            declaredMethod.invoke(jsInterface, instance, *arguments)
        } else {
            declaredMethod.invoke(jsInterface, *arguments)
        }

        return 0
    }

    @JavascriptInterface
    fun destroy(id: String) {
        instances.remove(id)
    }

    fun bind(instance: Any) {
        jsInterface = instance
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class InstanceMethod