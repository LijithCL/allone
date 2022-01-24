package com.example.allinoneapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.allinoneapp.databinding.ActivityMainBinding
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
            val intent = Intent(this,PhotoPage::class.java)
            startActivity(intent)
        }

        binding.btnMutlyImage.setOnClickListener {
            val intent = Intent(this,MultipleImages::class.java)
            startActivity(intent)
        }

        binding.btnLocation.setOnClickListener {
            val intent = Intent(this,Location_Activity::class.java)
            startActivity(intent)
        }

        binding.btnWebView.setOnClickListener {
            val intent = Intent(this,WebViewActivity::class.java)
            startActivity(intent)
        }

        binding.btnWorkManager.setOnClickListener {
            val periodicWorkRequest = PeriodicWorkRequest.Builder(MyPeriodicWork::class.java,20,TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance().enqueue(periodicWorkRequest)

        }

        binding.btnBroadNetconnection.setOnClickListener {
            val intent = Intent(this,BroadcastNetconnecionStatus::class.java)
            startActivity(intent)
        }

        binding.btnDateAndTimePiker.setOnClickListener {
            val intent = Intent(this,Date_and_Time_Picker::class.java)
            startActivity(intent)
        }
        binding.timePicker.setOnClickListener {
            val intent = Intent(this,TimePicker::class.java)
            startActivity(intent)
        }

        binding.callSms.setOnClickListener {
            val intent = Intent(this,CallandSMS::class.java)
            startActivity(intent)
        }

        binding.btnAlarm.setOnClickListener {
            val intent = Intent(this,AlarmActivity::class.java)
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

        startMainActivity()


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