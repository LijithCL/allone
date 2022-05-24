package com.example.allinoneapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivityIntentBinding

class IntentActivity : AppCompatActivity() {
    lateinit var binding:ActivityIntentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // send button on click listener
        binding.sendButton.setOnClickListener {
//            var intent = Intent(Intent.ACTION_SEND) // intent
//            intent.type = "text/plain"
//            intent.putExtra(Intent.EXTRA_EMAIL, "lijithcl333@gmail.com")
//            intent.putExtra(Intent.ACTION_ALL_APPS,"All Apps")
//            intent.putExtra(Intent.EXTRA_SUBJECT, "This is a dummy message")
//            intent.putExtra(Intent.EXTRA_TEXT, "Dummy test message")
//            startActivity(intent)
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "Here is the share content body"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        // View on click listener
        binding.buttonView.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW)
            startActivity(intent)
        }

    }
}