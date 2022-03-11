package com.example.allinoneapp.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Point
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.LocalOnlyHotspotCallback
import android.net.wifi.WifiManager.LocalOnlyHotspotReservation
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivityWiFiSahreBinding
import com.example.allinoneapp.utilClass.FileServerAsyncTask
import com.google.zxing.WriterException
import java.io.*
import java.net.Socket
import java.net.UnknownHostException


class WiFiSahreActivity : AppCompatActivity() {
    lateinit var binding: ActivityWiFiSahreBinding
    private var wifiManager: WifiManager? = null
    var currentConfig: WifiConfiguration? = null
    var hotspotReservation: LocalOnlyHotspotReservation? = null
    private var client: Socket? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityWiFiSahreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        turnOnHotspot()

        binding.btnConnect.setOnClickListener(View.OnClickListener {
//            FileServerAsyncTask(this)
//            val file =
//                File("/mnt/sdcard/input.jpg") //create file instance, file to transfer or any data
//            try {
//                client =
//                    Socket("10.0.0.83", 7661) // ip address and port number of ur hardware device
//                val mybytearray = ByteArray(file.length() as Int) //create a byte array to file
//                val fileInputStream = FileInputStream(file)
//                 val bufferedInputStream = BufferedInputStream(fileInputStream)
//                bufferedInputStream.read(mybytearray, 0, mybytearray.size) //read the file
//                val outputStream = client!!.getOutputStream()
//                outputStream.write(
//                    mybytearray,
//                    0,
//                    mybytearray.size
//                ) //write file to the output stream byte by byte
//                outputStream.flush()
//                bufferedInputStream.close()
//                outputStream.close()
//                client!!.close()
//                Toast.makeText(this,"File Sent",Toast.LENGTH_LONG).show()
////                text.setText("File Sent")
//            } catch (e: UnknownHostException) {
//                e.printStackTrace()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//
//
//
//            Thread {
//                // TODO Auto-generated method stub
//                try {
//                    client = Socket("10.0.0.83", 7661)
//                    val printwriter = OutputStreamWriter(
//                        client
//                            ?.getOutputStream(), "ISO-8859-1"
//                    )
//                    printwriter.write("any message")
//                    printwriter.flush()
//                    printwriter.close()
//                    client!!.close()
//                } catch (e: UnknownHostException) {
//                    e.printStackTrace()
//                } catch (e: IOException) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace()
//                }
//            }.start()

        })


    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun turnOnHotspot() {

        wifiManager!!.startLocalOnlyHotspot(object : LocalOnlyHotspotCallback() {
            @SuppressLint("SetTextI18n")
            override fun onStarted(reservation: LocalOnlyHotspotReservation) {
                super.onStarted(reservation)
                hotspotReservation = reservation
                currentConfig = hotspotReservation!!.wifiConfiguration
                Log.e(
                    "DANG", """THE PASSWORD IS: ${currentConfig!!.preSharedKey} 
                    SSID is : ${currentConfig!!.SSID}
                   BSSID : ${currentConfig!!.BSSID}
                    FQDN: ${currentConfig!!.FQDN}
                       networkId : ${currentConfig!!.networkId}
                       wepKeys : ${currentConfig!!.wepKeys}
                       status : ${currentConfig!!.status}
                       : ${currentConfig!!.httpProxy}
                       : ${currentConfig!!.providerFriendlyName}
                       : ${currentConfig!!.wepTxKeyIndex}"""
                )
                binding.txtLocalHotSpot.text="Hot Spot : ${currentConfig!!.SSID}\nPassword : ${currentConfig!!.preSharedKey}"
//                WIFI:S:<SSID>
//                T:<WPA|WEP|>
//                P:<password>;;
//                val qrCodeContent = "WIFI:S:"+currentConfig!!.SSID+";T:WPA;P:"+currentConfig!!.preSharedKey+";;"
                val qrCodeContent = mutableListOf<String>(currentConfig!!.SSID,currentConfig!!.preSharedKey).toString()
                val manager = getSystemService(WINDOW_SERVICE) as WindowManager
                val display: Display = manager.defaultDisplay
                val point = Point()
                display.getSize(point)
                val width: Int = point.x
                val height: Int = point.y
                var smallerDimension = if (width < height) width else height
                smallerDimension = smallerDimension * 3 / 4

                val qrgEncoder =
                    QRGEncoder(qrCodeContent, null, QRGContents.Type.TEXT, smallerDimension)

                try {
                    val  bitmap = qrgEncoder.encodeAsBitmap()
                    binding.imgQrscode.setImageBitmap(bitmap)
                } catch (e: WriterException) {
                    Log.v(TAG, e.toString())
                }

            }

            override fun onStopped() {
                super.onStopped()
                Log.e("DANG", "Local Hotspot Stopped")
                binding.txtLocalHotSpot.text="Local Hotspot Stopped"
            }

            override fun onFailed(reason: Int) {
                super.onFailed(reason)
                Log.e("DANG", "Local Hotspot failed to start")
                binding.txtLocalHotSpot.text="Local Hotspot failed to start"
            }
        }, Handler())


    }



}