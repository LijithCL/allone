package com.example.allinoneapp.view

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivityBluetoothBinding


class BluetoothActivity : AppCompatActivity() {

    lateinit var binding: ActivityBluetoothBinding
    lateinit var adapter: ArrayAdapter<Any>
    val list = ArrayList<Any>()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val filter = IntentFilter(
            "android.bluetooth.device.action.PAIRING_REQUEST"
        )
//        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)

        binding.btnBOn.setOnClickListener {
            if (mBluetoothAdapter.isEnabled) {
//                mBluetoothAdapter.disable()
                Toast.makeText(this,"Already On", Toast.LENGTH_SHORT).show()
            } else {
                mBluetoothAdapter.enable()
                Toast.makeText(this,"Bluetooth is ON", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBOff.setOnClickListener {
            if (mBluetoothAdapter.isEnabled) {
                mBluetoothAdapter.disable()
                Toast.makeText(this,"Bluetooth Off", Toast.LENGTH_SHORT).show()
            } else {
//                mBluetoothAdapter.enable()
                Toast.makeText(this,"Already Off", Toast.LENGTH_SHORT).show()
            }
        }


        val pairedDevices = mBluetoothAdapter.bondedDevices
        val listPaired = ArrayList<Any>()
        listPaired.clear()
        for (bt in (pairedDevices as MutableSet<BluetoothDevice>?)!!) {
            listPaired.add(bt.name)
        }
        Log.e("list",listPaired.toString())

        val adapterPaired: ArrayAdapter<*> = ArrayAdapter(this, android.R.layout.simple_list_item_1, listPaired)

        binding.listViewPairedDevice.adapter = adapterPaired

        binding.listViewPairedDevice.setOnItemClickListener { parent, view, position, id ->
            val element =  parent.getItemAtPosition(position) // The item that was clicked
            Log.e("item",element.toString())
        }


        binding.btnListDevice.setOnClickListener {
            mBluetoothAdapter.startDiscovery()
            list.clear()
            val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {

                    Log.e("recever","one")
                    val action = intent.action
                    if (BluetoothDevice.ACTION_FOUND == action) {
                        val device =
                            intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
//                    val deviceName = device?.name
//                    val deviceHardwareAddress = device?.address // MAC address
//                    Log.e("recever",deviceHardwareAddress.toString())
                        if (device!!.name!=null) {
                            list.add(
                                """
                            ${device.address}
                            """.trimIndent()
                            )
                            Log.e("BT", """${device.name}${device.address}""".trimIndent())
                        adapter = ArrayAdapter(applicationContext,android.R.layout.simple_list_item_1,list)
                            adapter.notifyDataSetChanged()
                            binding.listViewDevice.adapter = adapter
                        }
                    }
                }
            }
            registerReceiver(mReceiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        }
        binding.listViewDevice.setOnItemClickListener { parent, view, position, id ->
            val element =  parent.getItemAtPosition(position) // The item that was clicked
            Log.e("item",element.toString())
//            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val device = mBluetoothAdapter.getRemoteDevice("$element")
//            pairDevice(device)
        }



    }

    @SuppressLint("MissingPermission")
    fun connect(btDevice: BluetoothDevice?){
//        val id: UUID?= btDevice?.uuids?.get(0)!!.uuid
//        Log.e("item1",btDevice?.uuids.toString())
//        val bts = btDevice.createRfcommSocketToServiceRecord(id)
//        bts?.connect()
        val tManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val uuid = tManager.deviceId
        Log.e("item1",uuid.toString())

    }



//    fun pairDevice(device: BluetoothDevice?) {
//        val ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST"
//        val intent = Intent(ACTION_PAIRING_REQUEST)
//        val EXTRA_DEVICE = "android.bluetooth.device.extra.DEVICE"
//        intent.putExtra(EXTRA_DEVICE, device)
//        val EXTRA_PAIRING_VARIANT = "android.bluetooth.device.extra.PAIRING_VARIANT"
//        val PAIRING_VARIANT_PIN = 0
//        intent.putExtra(EXTRA_PAIRING_VARIANT, PAIRING_VARIANT_PIN)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
//    }


//    override fun onDestroy() {
//        unregisterReceiver(mReceiver)
//        super.onDestroy()
//    } ${device!!.name}

}