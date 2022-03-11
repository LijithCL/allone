package com.example.allinoneapp.view

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.lifecycle.ProcessCameraProvider
import com.example.allinoneapp.databinding.ActivityWiFiDirectBinding
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService


class WiFiDirectActivity : AppCompatActivity() {
    lateinit var binding: ActivityWiFiDirectBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    lateinit var adapter: ArrayAdapter<String>
    var wifiManager: WifiManager? = null
    val peers = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWiFiDirectBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        checkCameraPermission()
//        cameraExecutor = Executors.newSingleThreadExecutor()
//        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//
//        cameraProviderFuture.addListener(Runnable {
//            val cameraProvider = cameraProviderFuture.get()
//            bindPreview(cameraProvider)
//        }, ContextCompat.getMainExecutor(this))

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val wifiScanReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                if (success) {
                    scanSuccess()
                } else {
                    scanFailure()
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        applicationContext.registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager!!.startScan()
        if (!success) {
            // scan failure handling
            scanFailure()
        }



    }

    @SuppressLint("MissingPermission")
    private fun scanSuccess() {
        val results = wifiManager?.scanResults
       Log.e("result000",results.toString())
        peers.clear()
        if (results != null) {
            for (wd in results) {
//                var name = wd.SSID.subSequence(0, 11)
//                if(name.toString()=="AndroidShare") {
                    peers.add(wd.SSID)
//                }
//                peers1.add(wd.deviceAddress)
            }
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,peers)
        adapter.notifyDataSetChanged()
        binding.listWifi.adapter = adapter
        binding.listWifi.setOnItemClickListener { parent, view, position, id ->
            val element = parent.getItemAtPosition(position) // The item that was clicked
            Log.e("item", element.toString())
//            val key="875b588b6e70"

            val conf = WifiConfiguration()
            conf.SSID = "\"" + element.toString() + "\""
//            applicationContext.getSystemService(WIFI_SERVICE)

            var netId = wifiManager?.addNetwork(conf)
            if (netId == -1) netId = wifiManager?.configuredNetworks?.let {
                it.firstOrNull { it.SSID.trim('"') == element.toString().trim('"') }?.networkId ?: -1
            }
            Log.e("itemdddd", netId.toString())
            if (netId != null) {
                wifiManager?.enableNetwork(netId, true)
            }
        }
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager?.scanResults
        Log.e("result1111",results.toString())
    }







//    private fun checkCameraPermission() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.CAMERA
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            Intent().also {
//                it.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                it.data = Uri.fromParts("package", packageName, null)
//                startActivity(it)
//                finish()
//            }
//        }
//    }
//
//    @SuppressLint("UnsafeExperimentalUsageError")
//    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
//        val preview: Preview = Preview.Builder()
//            .build()
//        val cameraSelector: CameraSelector = CameraSelector.Builder()
//            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//            .build()
//        preview.setSurfaceProvider(previewView.createSurfaceProvider(null))
//
//        cameraProvider.bindToLifecycle(
//            this as LifecycleOwner,
//            cameraSelector,
//            preview
//        )
//
//        val imageAnalysis = ImageAnalysis.Builder()
//            .setTargetResolution(Size(1280, 720))
//            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//            .build()
//        imageAnalysis.setAnalyzer(cameraExecutor, analyzer)
//
//        cameraProvider.bindToLifecycle(
//            this as LifecycleOwner,
//            cameraSelector,
//            imageAnalysis,
//            preview
//        )
//    }

}