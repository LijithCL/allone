package com.example.allinoneapp

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivitySensorBinding


class SensorActivity : AppCompatActivity() {

    lateinit var binding:ActivitySensorBinding
    private var mSensorManager: SensorManager? = null
    private var cameraManager:CameraManager?=null
    private var cameraId:String=""
//    private val list: List<Sensor>? =null
    private var sensorlistener: SensorEventListener? =null
    var status = 1
    var changeStatus=0
    var torch:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySensorBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
//        val mList: List<Sensor> = mSensorManager!!.getSensorList(Sensor.TYPE_ALL)
//
//        for (i in 1 until mList.size) {
//            binding.textview.append(
//                """
//
//            ${mList[i].name}
//            ${mList[i].vendor}
//            ${mList[i].version}
//            """.trimIndent()
//            )
//        }

        sensorlistener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
            @SuppressLint("SetTextI18n")
            override fun onSensorChanged(event: SensorEvent) {
                val values = event.values
                binding.txtSensorList.text = """
                            x: ${values[0]}
                            y: ${values[1]}
                            z: ${values[2]}
                            """.trimIndent()

                if ("${values[1]}" > "1.000") {
                    if (changeStatus!=status) {
                        Toast.makeText(baseContext, "Alert!", Toast.LENGTH_SHORT).show()
                        changeStatus=1
                        torch=true
                        switchFlashLight(torch)
                    }
                }else{
                    changeStatus=0
                    torch=false
                    switchFlashLight(torch)
                }
            }
        }

         cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
             cameraId = cameraManager!!.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val list = mSensorManager!!.getSensorList(Sensor.TYPE_ACCELEROMETER)

//        Log.e("sensor","$list")

        if (list.size > 0) {
            mSensorManager!!.registerListener(sensorlistener, list[0], SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            Toast.makeText(this, "Error: No Accelerometer.", Toast.LENGTH_LONG).show()
        }

    }

    private fun switchFlashLight(status: Boolean) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager?.setTorchMode(cameraId, status)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }


//    override fun onStop() {
//        if (list == null) {
//                mSensorManager?.unregisterListener(sensorlistener)
//        }
//        super.onStop()
//    }
}