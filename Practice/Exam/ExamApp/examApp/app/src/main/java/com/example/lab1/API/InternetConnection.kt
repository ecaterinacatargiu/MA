package com.example.lab1.API

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast


class InternetConnection : Activity() {

    /** Called when the activity is first created.  */
    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState)
        this.registerReceiver(
            mConnReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    val mConnReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val noConnectivity =
                intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            val reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON)
            val isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false)
            val currentNetworkInfo =
                intent.getParcelableExtra<Parcelable>(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo
            val otherNetworkInfo =
                intent.getParcelableExtra<Parcelable>(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO) as NetworkInfo
            if (currentNetworkInfo.isConnected) {
                Toast.makeText(applicationContext, "Connected", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Not Connected", Toast.LENGTH_LONG).show()
            }
        }
    }
}