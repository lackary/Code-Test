package com.lacklab.app.codetest

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        var connectType: String? = null
    }

    private val TAG = Application::class.java.simpleName

    private var connectivityManager:ConnectivityManager? = null

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.i(TAG, "onAvailable")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.i(TAG, "onLost")
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            Log.i(TAG, "onCapabilitiesChanged")
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i(TAG, "TRANSPORT_WIFI")
                    connectType = "WIFI"
                } else if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i(TAG, "TRANSPORT_CELLULAR")
                    connectType = "MOBILE"
                }
            }

        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.i(TAG, "networkReceiver")
            val connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            when(intent?.action) {
                WifiManager.NETWORK_STATE_CHANGED_ACTION ->
                    Log.i(TAG,"NETWORK_STATE_CHANGED_ACTION")
                TelephonyManager.ACTION_PHONE_STATE_CHANGED ->
                    Log.i(TAG, "ACTION_PHONE_STATE_CHANGED")
            }
//            if (Build.VERSION.SDK_INT <= 28) {
//                // use getNetworkInfo to receive wifi network or mobile network
//            }

        }
    }

    override fun onCreate() {
        super.onCreate()

        try {
            val intentFilter = IntentFilter()
            intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
            intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            applicationContext.registerReceiver(networkReceiver, intentFilter)
            connectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager
            val builder = NetworkRequest.Builder()
            val request = builder.build()
            connectivityManager?.registerNetworkCallback(request, networkCallback)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}")
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        connectivityManager?.unregisterNetworkCallback(networkCallback)
        applicationContext.unregisterReceiver(networkReceiver)
    }
}