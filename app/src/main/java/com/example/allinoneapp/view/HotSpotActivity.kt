package com.example.allinoneapp.view

import android.annotation.SuppressLint
import android.content.*
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.LocalOnlyHotspotReservation
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivityHotSpotBinding
import com.example.allinoneapp.utilClass.WiFiDirectBroadcastReceiver


class HotSpotActivity : AppCompatActivity() {
    lateinit var binding: ActivityHotSpotBinding
    private var wifiManager: WifiManager? = null
    var currentConfig: WifiConfiguration? = null
    var hotspotReservation: LocalOnlyHotspotReservation? = null

    val manager: WifiP2pManager? by lazy(LazyThreadSafetyMode.NONE) {
        getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?
    }

    var channel: WifiP2pManager.Channel? = null
    var receiver: BroadcastReceiver? = null
    val intentFilter = IntentFilter()


    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHotSpotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wInfo = wifiManager!!.connectionInfo

//        // Extracting the information from the received connection info
//        val ipAddress = Formatter.formatIpAddress(wInfo.ipAddress)
//        val linkSpeed = wInfo.linkSpeed
//        val networkID = wInfo.networkId
//        val ssid = wInfo.ssid
//        val hssid = wInfo.hiddenSSID
//        val bssid = wInfo.bssid
//        Log.e("ipAddress",ipAddress)
//        Log.e("linkSpeed", linkSpeed.toString())
//        Log.e("networkID", networkID.toString())
//        Log.e("ssid",ssid)
//        Log.e("hssid", hssid.toString())
//        Log.e("bssid",bssid)
//        turnOnHotspot()




        binding.txtLocalHotSpot.setOnClickListener {
//            val intent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
//            startActivity(intent)

            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val cn = ComponentName("com.android.settings", "com.android.settings.TetherSettings")
            intent.component = cn
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)


        }

        binding.txtSend.setOnClickListener {
            receiver?.also { receiver ->
                registerReceiver(receiver, intentFilter)
            }
            channel = manager?.initialize(this, mainLooper, null)
            channel?.also { channel ->
                receiver = manager?.let { WiFiDirectBroadcastReceiver(it, channel, this) }
            }
            manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                }
                override fun onFailure(reasonCode: Int) {
                }
            })
        }





    }

    override fun onResume() {
        super.onResume()
        intentFilter.apply {
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        }

    }

    /* unregister the broadcast receiver */
//    override fun onPause() {
//        super.onPause()
//        receiver?.also { receiver ->
//            unregisterReceiver(receiver)
//        }
//    }





}