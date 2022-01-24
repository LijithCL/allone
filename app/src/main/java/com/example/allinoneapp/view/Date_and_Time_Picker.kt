package com.example.allinoneapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.example.allinoneapp.databinding.ActivityDateAndTimePickerBinding
import com.example.allinoneapp.databinding.ActivityMainBinding
import java.util.*
import android.app.DatePickerDialog

import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import androidx.annotation.RequiresApi


class Date_and_Time_Picker : AppCompatActivity() {
    lateinit var binding: ActivityDateAndTimePickerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateAndTimePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val today = Calendar.getInstance()
        binding.datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)) {
                view, year, month, day ->
            val month = month + 1
            val msg = "$day/$month/$year"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            binding.txtDate.text=msg
        }

//        binding.datePicker.updateDate(2021, 3-1, 15)


    }

}




