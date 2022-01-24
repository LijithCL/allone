package com.example.allinoneapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.allinoneapp.databinding.ActivityLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import java.io.IOException
import java.util.*


class Location_Activity : AppCompatActivity() {

    lateinit var binding: ActivityLocationBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val LOCATION_REQUEST_CODE = 101
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFindDirection.setOnClickListener {
            val intent = Intent(this, DirectionMap::class.java)
            startActivity(intent)
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btnLocation.setOnClickListener {
            val locationPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            if (locationPermission != PackageManager.PERMISSION_GRANTED){
                locationRequest()
            }else{
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(OnCompleteListener<Location?> { task ->
                        val location = task.result
                    Log.e("Location", location.toString())
                    try {
                        if (location != null) {
                            val geocoder= Geocoder(this, Locale.getDefault())
                            val address:List<Address> = geocoder.getFromLocation(location.latitude,location.longitude,1)
                            binding.latitude.text = location.latitude.toString() + ""
                            binding.longitude.text = location.longitude.toString() + ""
                            binding.locationAddress.text = Html.fromHtml(address[0].getAddressLine(0).toString())
                            Log.e("countryName", Html.fromHtml(address[0].countryName.toString()).toString())
                            Log.e("countryCode", Html.fromHtml(address[0].countryCode.toString()).toString())
                            Log.e("locality", Html.fromHtml(address[0].locality.toString()).toString())
//                            Log.e("phone", Html.fromHtml(address[0].phone.toString()).toString())
                            Log.e("postalCode", Html.fromHtml(address[0].postalCode.toString()).toString())
                            Log.e("subLocality", Html.fromHtml(address[0].subLocality.toString()).toString())
                            Log.e("adminArea", Html.fromHtml(address[0].adminArea.toString()).toString())
                            Log.e("subAdminArea", Html.fromHtml(address[0].subAdminArea.toString()).toString())
                            Log.e("locale", Html.fromHtml(address[0].locale.toString()).toString())


                        } else {
                            Toast.makeText(this,"Location access Failed",Toast.LENGTH_LONG).show()
                        }

                    }catch (e: IOException){
                        Toast.makeText(this,"Location Failed",Toast.LENGTH_LONG).show()
                    }

                    })
            }
        }


    }



    private fun locationRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


}