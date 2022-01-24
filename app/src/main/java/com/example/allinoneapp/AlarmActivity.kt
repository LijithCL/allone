package com.example.allinoneapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivityAlarmBinding
import java.util.*


class AlarmActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityAlarmBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.setAlarm.setOnClickListener(this)
        binding.cancelAlarm.setOnClickListener {
            AlarmCancel()
        }

        binding.stopAlarm.setOnClickListener {
            AlarmStop()
        }



    }

//    private fun alarmSet() {
//        val i: Int = binding.alarmTimer.getText().toString().toInt()
//        val intent = Intent(this, Alarm::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(this.applicationContext, 234324243, intent, 0)
//        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        alarmManager[AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + i * 1000] = pendingIntent
//        Toast.makeText(this, "Alarm set in $i seconds", Toast.LENGTH_LONG).show()
//    }


    private fun AlarmStop() {
        Alarm().stopAlarms()

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        val cal = Calendar.getInstance()
        cal[cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH], binding.time.hour,
                binding.time.minute] = 0
        var time = (cal.timeInMillis-System.currentTimeMillis())/1000
//        Log.e("current time",cal.timeInMillis.toString())
//        Log.e("system time",System.currentTimeMillis().toString())
        AlarmSet(time)
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private fun AlarmSet(time: Long) {

//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, Alarm::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,AlarmManager.INTERVAL_DAY,
//            pendingIntent)
//        Toast.makeText(this, "Your Alarm is Set ", Toast.LENGTH_LONG).show()

        val intent = Intent(this, Alarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this.applicationContext, 234324243, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 1000] = pendingIntent
        Toast.makeText(this, "Alarm set in $time seconds", Toast.LENGTH_LONG).show()

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun AlarmCancel() {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, Alarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Your Alarm is Cancel", Toast.LENGTH_LONG).show()
    }



}