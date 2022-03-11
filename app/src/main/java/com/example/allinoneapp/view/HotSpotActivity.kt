package com.example.allinoneapp.view

import android.annotation.SuppressLint
import android.content.*
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.LocalOnlyHotspotReservation
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.format.Formatter
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.R
import com.example.allinoneapp.databinding.ActivityHotSpotBinding
import com.example.allinoneapp.utilClass.WiFiDirectBroadcastReceiver


class HotSpotActivity : AppCompatActivity() {
    lateinit var binding: ActivityHotSpotBinding
    val diceRange = 1..6


    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHotSpotBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val randomNumber = diceRange.random()
        binding.btnSend.setOnClickListener { diceRoll() }

    }

    private fun diceRoll() {
        val diceNumber=Dice(6).roll()
        when (diceNumber) {
            1 -> binding.imgDice.setImageResource(R.drawable.dice_1)
            2 -> binding.imgDice.setImageResource(R.drawable.dice_2)
            3 -> binding.imgDice.setImageResource(R.drawable.dice_3)
            4 -> binding.imgDice.setImageResource(R.drawable.dice_4)
            5 -> binding.imgDice.setImageResource(R.drawable.dice_5)
            6 -> binding.imgDice.setImageResource(R.drawable.dice_6)
        }
        binding.txtDiceNumber.text=diceNumber.toString()
        binding.imgDice.contentDescription = diceNumber.toString()
    }
}

class Dice(private val numSides: Int) {

    fun roll(): Int {
        return (1..numSides).random()
    }
}
