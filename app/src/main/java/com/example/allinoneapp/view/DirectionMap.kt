package com.example.allinoneapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.allinoneapp.R
import com.example.allinoneapp.utilClass.MapData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.Places
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import io.teliver.sdk.core.Teliver
import io.teliver.sdk.models.MarkerOption
import io.teliver.sdk.models.TrackingBuilder
import io.teliver.sdk.models.TripBuilder
import java.util.*


class DirectionMap : AppCompatActivity(), OnMapReadyCallback {

     var mMap : GoogleMap? =null
    lateinit var supportMapFragment: SupportMapFragment
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var currentPolyline :Polyline? = null
    var apiKey : String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direction_map)

//        val ai: ApplicationInfo = applicationContext.packageManager
//            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
//        val value = ai.metaData["com.google.android.geo.API_KEY"]
//        apiKey = value.toString()
//
//        if (!Places.isInitialized()) {
//            Places.initialize(applicationContext, apiKey)
//        }

        supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

//        Teliver.init(this,"c04f467a654b4c37b72f84d4a41bed6d")
//        Teliver.startTrip(TripBuilder("Tracking_Id").build())
//        Teliver.startTracking(TrackingBuilder(MarkerOption("Tracking_Id")).build());



    }

    override fun onMapReady(googleMap: GoogleMap){

        getLocation(googleMap)

//        object : CountDownTimer(100000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                getLocation(googleMap)
//            }
//            override fun onFinish() {
//
//            }
//        }.start()

    }

    @SuppressLint("MissingPermission")
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

//                val geocoder= Geocoder(this, Locale.getDefault())
//                val address:List<Address> = geocoder.getFromLocation(location.latitude,location.longitude,1)
//                mMap?.clear()
                mMap=googleMap
                val Thrippunithura = LatLng(location.latitude,location.longitude)
                mMap!!.addMarker(MarkerOptions().position(Thrippunithura).title("Alignminds"))
                mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(Thrippunithura, 15f))





                Log.e("location", location.latitude.toString())

                val kaloor = LatLng(9.995006,76.292173)
                mMap!!.addMarker(MarkerOptions().position(kaloor).title("Kaloor"))

                val urll = getDirectionURL(Thrippunithura, kaloor, apiKey)
                GetDirection(urll).execute()

//                var soures =Html.fromHtml(address[0].subLocality.toString()).toString()
//                var destination ="kaloor"
//               displayTrack(soures,destination)

            } else {
                Toast.makeText(this,"Location access Failed", Toast.LENGTH_LONG).show()
            }
        })





    }


//    override fun onMapReady(p0: GoogleMap?) {
//        mMap = p0!!
//        val originLocation = LatLng(originLatitude, originLongitude)
//        mMap.clear()
//        mMap.addMarker(MarkerOptions().position(originLocation))
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 18F))
//    }

    private fun getDirectionURL(origin:LatLng, dest:LatLng, secret: String) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$secret"
    }

    @SuppressLint("StaticFieldLeak")
    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body().string()

            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data, MapData::class.java)
                val path =  ArrayList<LatLng>()
                for (i in 0 until respObj.routes[0].legs[0].steps.size){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.GREEN)
                lineoption.geodesic(true)
            }
            mMap?.addPolyline(lineoption)
        }
    }

    fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }



    private fun displayTrack(soures: String, destination: String) {
        val uri = Uri.parse("https://www.google.co.in/maps/dir/$soures/$destination")
        Log.e("phone", "$uri")
        val intent = Intent(Intent.ACTION_VIEW,uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }


}