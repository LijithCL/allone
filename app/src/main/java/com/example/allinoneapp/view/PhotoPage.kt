package com.example.allinoneapp.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.allinoneapp.R
import com.example.allinoneapp.databinding.ActivityPhotoPageBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class PhotoPage : AppCompatActivity() {

    private val STORAGE_REQUEST_CODE = 101
    private val CAMERA_REQUEST_CODE=123;
    private val CAMERA_IMAGE = 1000;
    private val GALLERY_IMAGE = 1001;

    lateinit var binding:ActivityPhotoPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnImageTake.setOnClickListener {

            setupPermissions()
        }

    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet)
        bottomSheetDialog.show()

        bottomSheetDialog.findViewById<ImageView>(R.id.txt_cameraImage)?.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,CAMERA_IMAGE)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.findViewById<ImageView>(R.id.txt_galaryImage)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,GALLERY_IMAGE)
            bottomSheetDialog.dismiss()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1000) {
            if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_IMAGE && data != null) {
                binding.imgViewPhoto.setImageBitmap(data.extras!!.get("data") as Bitmap)
            }
        }else{
            if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_IMAGE && data != null) {
                binding.imgViewPhoto.setImageURI(data.data)
            }
        }
    }

    private fun setupPermissions() {
        val storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val cameraPermission= ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)

        if (storagePermission == PackageManager.PERMISSION_GRANTED && cameraPermission==PackageManager.PERMISSION_GRANTED) {
            showBottomSheetDialog()
        }else{
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                    storageRequest()
            }
            if(cameraPermission!=PackageManager.PERMISSION_GRANTED) {
                    cameraRequest()
            }
        }

    }

    private fun storageRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_REQUEST_CODE)
    }

    private  fun  cameraRequest(){
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

}