package io.github.richarddorian.nativebridge.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

fun isNetworkAvailable(activity: Activity): Boolean {
    val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager?.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}