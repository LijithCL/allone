package com.example.allinoneapp.utilClass

import android.R
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.allinoneapp.view.HotSpotActivity

class WiFiDirectBroadcastReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val activity: HotSpotActivity
) : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        lateinit var adapter: ArrayAdapter<String>
        val action: String? = intent.action
        when (action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                // Check to see if Wi-Fi is enabled and notify appropriate activity
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                when (state) {
                    WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {
                        Toast.makeText(context,"Wi-Fi P2P is enabled",Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(context,"Wi-Fi P2P is not enabled",Toast.LENGTH_LONG).show()
                    }
                }


            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                // Call WifiP2pManager.requestPeers() to get a list of current peers
//                manager.requestPeers(channel) { peers: WifiP2pDeviceList? ->
//                    // Handle peers list
////                    WifiP2pManager.PeerListListener(peers)
//                    Log.e("peer list",peers.toString())
//                    activity.binding.txtDevice.text= peers.toString()
//
//                }
                val peers = mutableListOf<String>()
                val peers1 = mutableListOf<String>()
                val peerListListener = WifiP2pManager.PeerListListener { peerList ->
                    val refreshedPeers = peerList.deviceList
                    if (refreshedPeers != peers) {
                        peers.clear()
//                        peers.addAll(refreshedPeers.indices)
                        for (wd in peerList.deviceList) {
                            peers.add(wd.deviceName)
                            peers1.add(wd.deviceAddress)
                        }
//                        activity.binding.txtDevice.text= peers.toString()
                        adapter = ArrayAdapter(activity, R.layout.simple_list_item_1,peers)
                        adapter.notifyDataSetChanged()
                        activity.binding.txtDevice.adapter = adapter

                        // If an AdapterView is backed by this data, notify it
                        // of the change. For instance, if you have a ListView of
                        // available peers, trigger an update.
//                        (listAdapter as WiFiPeerListAdapter).notifyDataSetChanged()

                        // Perform any other updates needed based on the new list of
                        // peers connected to the Wi-Fi P2P network.
                    }
                    activity.binding.txtDevice.setOnItemClickListener { parent, view, position, id ->
                        val element =  parent.getItemAtPosition(position) // The item that was clicked
                        Log.e("item",element.toString())
                        val device = peers1[0]

                        val config = WifiP2pConfig().apply {
                            deviceAddress = device
                            wps.setup = WpsInfo.PBC
                        }

                        manager.connect(channel, config, object : WifiP2pManager.ActionListener {

                            override fun onSuccess() {
                                // WiFiDirectBroadcastReceiver notifies us. Ignore for now.
                                Toast.makeText(
                                    activity,
                                    "Connected.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                            override fun onFailure(reason: Int) {
                                Toast.makeText(
                                    activity,
                                    "Connect failed. Retry.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
//
                    }

                    if (peers.isEmpty()) {
                        Log.e(TAG, "No devices found")
                        return@PeerListListener
                    }
                }

                manager.requestPeers(channel, peerListListener)

//                override fun connect() {
                    // Picking the first device found on the network.
//                    val device = peers1[0]
//
//                    val config = WifiP2pConfig().apply {
//                        deviceAddress = device
//                        wps.setup = WpsInfo.PBC
//                    }
//
//                    manager.connect(channel, config, object : WifiP2pManager.ActionListener {
//
//                        override fun onSuccess() {
//                            // WiFiDirectBroadcastReceiver notifies us. Ignore for now.
//                            Toast.makeText(
//                                activity,
//                                "Connected.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//
//
//                        override fun onFailure(reason: Int) {
//                            Toast.makeText(
//                                activity,
//                                "Connect failed. Retry.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    })
//                }


            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                // Respond to new connection or disconnections
            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                // Respond to this device's wifi state changing

            }
        }
    }
}
