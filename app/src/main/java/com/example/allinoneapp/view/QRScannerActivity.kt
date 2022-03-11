package com.example.allinoneapp.view

import android.app.Activity
import android.content.Intent
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivityQrscannerBinding
import com.google.zxing.integration.android.IntentIntegrator
import ezvcard.Ezvcard
import ezvcard.VCard
import java.lang.String


class QRScannerActivity : AppCompatActivity() {
    lateinit var binding: ActivityQrscannerBinding
    private lateinit var mQrResultLauncher : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQrscannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mQrResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK) {
                val result = IntentIntegrator.parseActivityResult(it.resultCode, it.data)

                if(result.contents != null) {
                    // Do something with the contents (this is usually a URL)
                    Log.e("QR=",result.contents)
//                    Log.e("QRP=",result.barcodeImagePath)
                    val res=result.contents.toCharArray()
                    Log.e("res", res[0].toString())
//                    val wifiConfig = WifiConfiguration()
//                    wifiConfig.SSID = String.format("\"%s\"", result.contents[0])
//                    wifiConfig.preSharedKey = String.format("\"%s\"", result.contents[1])
//
//                    val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
//
//                    val netId = wifiManager.addNetwork(wifiConfig)
//                    wifiManager.disconnect()
//                    wifiManager.enableNetwork(netId, true)
//                    wifiManager.reconnect()
//
//                    val vcard: VCard = Ezvcard.parse(result.contents).first()
                    //setting values to textviews

                    // "given name" is the first name
                    //setting values to textviews

                    // "given name" is the first name
//                    etFirstName.setText(vcard.getStructuredName().getGiven())
//
//                    // "family name" is the last name
//
//                    // "family name" is the last name
//                    etLastName.setText(vcard.getStructuredName().getFamily())

                }
            }
        }

        // Starts scanner on Create of Overlay (you can also call this function using a button click)
        startScanner()

    }

    private fun startScanner() {
        val scanner = IntentIntegrator(this)
        // QR Code Format
        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        // Set Text Prompt at Bottom of QR code Scanner Activity
        scanner.setPrompt("QR Code Scanner Prompt Text")
        // Start Scanner (don't use initiateScan() unless if you want to use OnActivityResult)
        mQrResultLauncher.launch(scanner.createScanIntent())
    }

}