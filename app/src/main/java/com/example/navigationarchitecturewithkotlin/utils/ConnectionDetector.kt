package com.example.navigationarchitecturewithkotlin.utils

import android.content.Context
import android.net.ConnectivityManager

class ConnectionDetector {
    companion object {

        fun networkStatus(context: Context): Boolean {

            return isWifiAvailable(context) || isMobileNetworkAvailable(context)
        }

        private fun isMobileNetworkAvailable(ctx: Context): Boolean {
            val connecManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val myNetworkInfo = connecManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

            return myNetworkInfo != null && myNetworkInfo.isConnected

        }

        private fun isWifiAvailable(ctx: Context): Boolean {
            val myConnManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

            return myNetworkInfo != null && myNetworkInfo.isConnected

        }
    }
}
