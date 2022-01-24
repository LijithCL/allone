package com.example.allinoneapp

import android.content.BroadcastReceiver
import android.content.Context
import android.media.MediaPlayer

import android.content.Intent
import android.provider.Settings
import android.util.Log


class Alarm : BroadcastReceiver() {
    var al : MediaPlayer? =null
    override fun onReceive(context: Context, intent: Intent) {
        al = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI)
        al?.start()
        Log.e("Alarm","Alarm Ringing")
    }

    fun stopAlarms() {
        al?.stop()
        Log.e("Alarm","Alarm Stop")
    }
}