package com.hrithik.cricketplayers

import android.app.Application
import android.net.ConnectivityManager

/**
 * This is the application of the our app
 */
class MyApplication : Application() {

    // Method that check Network Availability
    fun isOffline() : Boolean{
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val dataNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return !(wifiNetworkInfo!!.isConnected || dataNetworkInfo!!.isConnected)
    }

}