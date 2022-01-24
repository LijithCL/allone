package com.example.allinoneapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.tasks.OnCompleteListener
import java.util.*
import androidx.core.content.ContextCompat

import android.os.Build
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions





class DirectionMap : AppCompatActivity(), OnMapReadyCallback {

    lateinit var mMap : GoogleMap
    lateinit var supportMapFragment: SupportMapFragment
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var currentPolyline :Polyline? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direction_map)

        supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



    }

    override fun onMapReady(googleMap: GoogleMap){
        getLocation(googleMap)

    }

    private fun getLocation(googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(OnCompleteListener<Location?> { task ->
            val location = task.result
            if (location != null) {

                val geocoder= Geocoder(this, Locale.getDefault())
                val address:List<Address> = geocoder.getFromLocation(location.latitude,location.longitude,1)

                mMap=googleMap
                val Thrippunithura = LatLng(location.latitude,location.longitude)
                mMap.addMarker(MarkerOptions().position(Thrippunithura).title("Alignminds"))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Thrippunithura, 15f))
//
//                val kaloor = LatLng(9.995006,76.292173)
//                mMap.addMarker(MarkerOptions().position(kaloor).title("Kaloor"))

                var soures =Html.fromHtml(address[0].subLocality.toString()).toString()
                var destination ="kaloor"
//               displayTrack(soures,destination)

            } else {
                Toast.makeText(this,"Location access Failed", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayTrack(soures: String, destination: String) {
        val uri = Uri.parse("https://www.google.co.in/maps/dir/$soures/$destination")
        Log.e("phone", "$uri")
        val intent = Intent(Intent.ACTION_VIEW,uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }


}