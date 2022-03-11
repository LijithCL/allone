package com.example.allinoneapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.allinoneapp.R
import com.example.allinoneapp.databinding.ActivitySambleBinding

class SambleActivity : AppCompatActivity() {
    lateinit var binding: ActivitySambleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySambleBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnClick.setOnClickListener {
//            var tip= binding.txtEditText.text.toString()
//            tip = kotlin.math.ceil(tip.toDouble()).toString()
//            binding.txtText.text=tip
//        }

    }
}

//val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
//binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
