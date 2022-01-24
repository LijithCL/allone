package com.example.allinoneapp.utilClass

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object ConnectionStatus {

    fun call(context: Context):Boolean {
        val connectionManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

}
