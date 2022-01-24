package com.example.allinoneapp.utilClass

import android.app.AlertDialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.R

class BroadcastNetconnecionStatus : AppCompatActivity(),
    ConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var alertDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_netconnecion_status)

        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        alertDialog= AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Internet Connection Alert")
            .setMessage("Please Check Your Internet Connection")
            .show()

    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {

        if(isConnected){
            alertDialog.dismiss()
           Toast.makeText(this,"Internet Connected",Toast.LENGTH_LONG).show()
        }else{
            alertDialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }


//    private fun netAlert() {
//        AlertDialog.Builder(this)
//            .setIcon(android.R.drawable.ic_dialog_alert)
//            .setTitle("Internet Connection Alert")
//            .setMessage("Please Check Your Internet Connection")
//            .show()
//    }
}