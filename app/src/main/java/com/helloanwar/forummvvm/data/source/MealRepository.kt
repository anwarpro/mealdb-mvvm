package com.helloanwar.forummvvm.data.source

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.helloanwar.forummvvm.data.source.local.LocalMealSource
import com.helloanwar.forummvvm.data.source.remote.RemoteMealSource
import javax.inject.Inject

class MealRepository @Inject constructor(
    val remote: RemoteMealSource,
    val local: LocalMealSource
) {
    val isConnected = false
    suspend fun <T> getMeals() = if (isConnected) remote.getMeals<T>() else local.getMeals()
}


fun Context.isConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            //It will check for both wifi and cellular network
            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        }
        return false
    } else {
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}