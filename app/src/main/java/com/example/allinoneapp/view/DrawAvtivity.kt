package com.example.allinoneapp.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivityDrawAvtivityBinding
import com.example.allinoneapp.utilClass.MyView
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer


class DrawAvtivity : AppCompatActivity() {
    lateinit var binding: ActivityDrawAvtivityBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDrawAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val outerCircle = MyView(this,200,"#C16C8B")
//       binding.activityMainRelativeLayout.addView(outerCircle)
//        val innerCircle = MyView(this,150,"#ffffff")
//        binding.activityMainRelativeLayout.addView(innerCircle)

        binding.activityMainRelativeLayout.setOnTouchListener(touchListener)

        binding.btnDisplay.setOnClickListener { displayText() }

        binding.btnClear.setOnClickListener { clearText() }



    }

    private fun clearText() {
        binding.activityMainRelativeLayout.removeAllViews()
//        setBackgroundColor(Color.parseColor("#ffffff"))
//        binding.textImage.text=""
    }

    private fun displayText() {

        binding.activityMainRelativeLayout.setDrawingCacheEnabled(true)
        binding.activityMainRelativeLayout.buildDrawingCache()
        val bm: Bitmap = binding.activityMainRelativeLayout.getDrawingCache()


//        val bitmap = BitmapFactory.decodeResource(resources, com.example.allinoneapp.R.drawable.aspectratio)

        val textRecognizer = TextRecognizer.Builder(applicationContext).build()

        val imageFrame: Frame =
            Frame.Builder()
                .setBitmap(bm) // your image bitmap
                .build()


        var imageText = ""
        val textBlocks = textRecognizer.detect(imageFrame)

        for (i in 0 until textBlocks.size()) {
            val textBlock = textBlocks[textBlocks.keyAt(i)]
            imageText = textBlock.value // return string
            Log.e("Text",textBlock.value)
            binding.textImage.text=imageText
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private var touchListener: View.OnTouchListener = View.OnTouchListener { v, event ->
        if (event.actionMasked == MotionEvent.ACTION_DOWN || event.actionMasked == MotionEvent.ACTION_MOVE || event.actionMasked == MotionEvent.ACTION_UP) {
            var xAxis = event.x
            var yAxis = event.y

            Log.e("x axis",xAxis.toString())
            val outerCircle = MyView(applicationContext,10,"#C16C8B",xAxis.toInt(),yAxis.toInt())
            binding.activityMainRelativeLayout.addView(outerCircle)

        }

        // let the touch event pass on to whoever needs it
        true
    }


}

