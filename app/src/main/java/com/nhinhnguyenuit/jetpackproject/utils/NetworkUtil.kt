package com.nhinhnguyenuit.jetpackproject.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}