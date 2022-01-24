package com.example.allinoneapp.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivitySystemConnectivityBinding
import android.content.Intent
import android.provider.Settings
import android.content.DialogInterface





class SystemConnectivity : AppCompatActivity() {

    lateinit var binding: ActivitySystemConnectivityBinding

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemConnectivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        wManager.isWifiEnabled=false

        binding.btnWifi.setOnClickListener {

            if (wManager.isWifiEnabled){
//                wManager.isWifiEnabled=false
//                binding.btnWifi.text = "Disable wifi"
                Toast.makeText(this,"wifi already Enabled",Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                startActivity(intent)
//                wManager.isWifiEnabled=true
//                binding.btnWifi.text = "Enable wifi"
//                Toast.makeText(this,"wifi enabled",Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnBluetooth.setOnClickListener {

            if (mBluetoothAdapter.isEnabled) {
                mBluetoothAdapter.disable()
                binding.btnBluetooth.text = "Bluetooth is OFF"
                Toast.makeText(this,"Bluetooth is OFF",Toast.LENGTH_SHORT).show()
            } else {
                mBluetoothAdapter.enable()
                binding.btnBluetooth.text = "Bluetooth is ON"
                Toast.makeText(this,"Bluetooth is ON",Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnGps.setOnClickListener {

            val mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            if (!mGPS){
                buildAlertMessageNoGps()
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
            }else{
                Toast.makeText(this,"GPS already Enabled",Toast.LENGTH_SHORT).show()
            }


        }

    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS is disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert: AlertDialog = builder.create()
        alert.show()
    }
}