package com.example.allinoneapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.allinoneapp.databinding.ActivityMainBinding
import com.example.allinoneapp.utilClass.BroadcastNetconnecionStatus
import com.example.allinoneapp.utilClass.MyPeriodicWork
import com.example.allinoneapp.view.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.util.concurrent.TimeUnit


class HomePage : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    var title:String=""
    var body:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.photo.setOnClickListener {
            val intent = Intent(this, PhotoPage::class.java)
            startActivity(intent)
        }

        binding.btnMutlyImage.setOnClickListener {
            val intent = Intent(this, MultipleImages::class.java)
            startActivity(intent)
        }

        binding.btnLocation.setOnClickListener {
            val intent = Intent(this, Location_Activity::class.java)
            startActivity(intent)
        }

        binding.btnWebView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }

        binding.btnWorkManager.setOnClickListener {
            val periodicWorkRequest = PeriodicWorkRequest.Builder(MyPeriodicWork::class.java,20,TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance().enqueue(periodicWorkRequest)

        }

        binding.btnBroadNetconnection.setOnClickListener {
            val intent = Intent(this, BroadcastNetconnecionStatus::class.java)
            startActivity(intent)
        }

        binding.btnDateAndTimePiker.setOnClickListener {
            val intent = Intent(this, Date_and_Time_Picker::class.java)
            startActivity(intent)
        }
        binding.timePicker.setOnClickListener {
            val intent = Intent(this, TimePicker::class.java)
            startActivity(intent)
        }

        binding.callSms.setOnClickListener {
            val intent = Intent(this, CallandSMS::class.java)
            startActivity(intent)
        }

        binding.btnAlarm.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }

        binding.notification.setOnClickListener {

            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)

        }
        binding.openSystemConnectivities.setOnClickListener {

            val intent = Intent(this, SystemConnectivity::class.java)
            startActivity(intent)

        }

        binding.btnSpinner.setOnClickListener {

            val intent = Intent(this, SpinnerActivity::class.java)
            startActivity(intent)

        }

        binding.btnOptionMenu.setOnClickListener {

            val intent = Intent(this, OptionMenuActivity::class.java)
            startActivity(intent)

        }

        binding.btnSensor.setOnClickListener {

            val intent = Intent(this, SensorActivity::class.java)
            startActivity(intent)

        }

        binding.stickyHeader.setOnClickListener {

            val intent = Intent(this, StickyHeader::class.java)
            startActivity(intent)

        }

        binding.btnDraw.setOnClickListener {

            val intent = Intent(this, DrawAvtivity::class.java)
            startActivity(intent)

        }

        binding.btnExpandRecyclerview.setOnClickListener {

            val intent = Intent(this, circleAcivity::class.java)
            startActivity(intent)

        }

        binding.btnBottomNav.setOnClickListener {

            val intent = Intent(this, BottomNavigation::class.java)
            startActivity(intent)

        }

        binding.btnBluetooth.setOnClickListener {

            val intent = Intent(this, BluetoothActivity::class.java)
            startActivity(intent)

        }

        binding.btnHotSpot.setOnClickListener {

            val intent = Intent(this, HotSpotActivity::class.java)
            startActivity(intent)

        }

        binding.btnWifiShare.setOnClickListener {

            val intent = Intent(this, WiFiSahreActivity::class.java)
            startActivity(intent)

        }

        binding.btnGallery.setOnClickListener {

            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)

        }

        binding.btn.setOnClickListener {

            val intent = Intent(this, SambleActivity::class.java)
            startActivity(intent)

        }

        binding.btnQr.setOnClickListener {

            val intent = Intent(this, QRScannerActivity::class.java)
            startActivity(intent)

        }


//        Toast.makeText(this,"release variant",Toast.LENGTH_LONG).show()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("Failed message", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
            Log.e("Token",token)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

         title= intent.getStringExtra("title").toString()
        body= intent.getStringExtra("body").toString()
//        Toast.makeText(this,title,Toast.LENGTH_LONG).show()

//        startMainActivity()

////        bellow code is used to set two number after decimal point
//        val number = 0.045
//        var filterUserPrice: String? = "%.1f".format(number)
//        Log.e("afterRoundoff"," : $filterUserPrice")

        var number = 2.20
        var number1 =number%number.toInt()
        if(number1==0.0) {
            Log.e("result", number.toInt().toString())
        }else{
            Log.e("result1", String.format("%.2f", number).toDouble().toString())
        }

    }

    private fun startMainActivity() {


        mRunnable = Runnable {
            val intt=Intent(this, NotificationActivity::class.java)
            intt.putExtra("title",title)
            intt.putExtra("body",body)
            startActivity(intt)
            finish()
        }

        mHandler = Handler()

        mHandler.postDelayed(mRunnable, 1000)
    }


}