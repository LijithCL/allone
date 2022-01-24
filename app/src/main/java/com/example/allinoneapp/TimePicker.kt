package com.example.allinoneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.allinoneapp.databinding.ActivityMainBinding
import com.example.allinoneapp.databinding.ActivityTimePickerBinding

class TimePicker : AppCompatActivity() {

    lateinit var binding: ActivityTimePickerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        OnClickTime()

    }

    private fun OnClickTime() {

        binding.timePicker.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 ->
                    { hour += 12
                        am_pm = "AM"
                    }
                hour == 12 ->
                    am_pm = "PM"
                hour > 12 ->
                    { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
//            if (binding.txtTime != null) {
                val houre = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "Time is: $houre : $min $am_pm"
                binding.txtTime.text = msg
                binding.txtTime.visibility = View.VISIBLE
//            }
        }
    }

}