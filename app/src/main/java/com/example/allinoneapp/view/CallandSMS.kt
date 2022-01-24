package com.example.allinoneapp.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import com.example.allinoneapp.databinding.ActivityCallandSmsBinding
import com.example.allinoneapp.databinding.ActivityMainBinding

class CallandSMS : AppCompatActivity() {
    lateinit var binding: ActivityCallandSmsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallandSmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCall.setOnClickListener {
            var phoneNumber = binding.txtPhoneNumber.text.toString()
            if (phoneNumber==""){
                Toast.makeText(this,"Enter Phone Number",Toast.LENGTH_SHORT).show()
            }else if(phoneNumber.length!=10){
                Toast.makeText(this,"Enter Valid Phone Number",Toast.LENGTH_SHORT).show()
            }else {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:+91" + phoneNumber))
                startActivity(intent)
            }
        }

        binding.btnSms.setOnClickListener {
            sendSMS()
        }
    }

    private fun sendSMS() {
        var phoneNumbers=binding.txtPhoneNumber.text.toString()
        var txtMessage=binding.txtMessage.text.toString()
        if (phoneNumbers==""||txtMessage=="") {
            Toast.makeText(this, "Enter Phone Number and massage", Toast.LENGTH_SHORT).show()
        }else if (phoneNumbers.length!=10){
            Toast.makeText(this,"Enter Valid Phone Number",Toast.LENGTH_SHORT).show()
        }else {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumbers, null, txtMessage, null, null)
            Toast.makeText(this,"sms send",Toast.LENGTH_SHORT).show()

            binding.txtPhoneNumber.setText("")
            binding.txtMessage.setText("")
        }
    }

}