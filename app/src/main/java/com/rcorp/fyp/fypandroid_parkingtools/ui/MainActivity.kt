package com.rcorp.fyp.fypandroid_parkingtools.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.rcorp.fyp.fypandroid20.mapClustering.ParkingMarkerManager
import com.rcorp.fyp.fypandroid_parkingtools.R
import com.rcorp.fyp.fypandroid_parkingtools.data.DataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    var mMap : GoogleMap? = null
    var mUserLocation : Location? = null
    var mParkingMarkerManager : ParkingMarkerManager? = null
    var mParkingActive : Boolean = false
    private val mInterval : Long = 1000 // 5 seconds by default, can be changed later
    private var mHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        this.map_view.onCreate(savedInstanceState)
        this.map_view.getMapAsync(this)
        mHandler = Handler()

        fab.setOnClickListener { view ->
            if (mParkingActive)
            {
                fab.colorNormal = Color.parseColor("#4caf50")
                fab.colorPressed = Color.parseColor("#80e27e")
                fab.setImageResource(R.drawable.ic_play)
            } else {
                fab.colorNormal = Color.parseColor("#e53935")
                fab.colorPressed = Color.parseColor("#ff6f60")
                fab.setImageResource(R.drawable.ic_stop)
            }
            mParkingActive = !mParkingActive
            if (mParkingActive)
                startRepeatingTask()
            else
                stopRepeatingTask()
        }
    }

    var mParkingChecker: Runnable = object : Runnable {
        override fun run() {
            if (mUserLocation != null) {
                DataManager.parked(mUserLocation!!).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                        { _ ->
                            Log.d("Debug", "Vous ete garer")
                        },
                        { e ->
                            Snackbar.make(main_layout, "Erreur impossible de vous garé", Snackbar.LENGTH_LONG).show()
                        })
            }
            if (mParkingActive)
                mHandler?.postDelayed(this, mInterval)
        }
    }

    fun startRepeatingTask() {
        mParkingChecker.run()
    }

    fun stopRepeatingTask() {
        mHandler?.removeCallbacks(mParkingChecker)
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        if (checkNeededPermissions())
            initMap()
    }

    private fun askLocationPermission () {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this as Activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
        } else {
            ActivityCompat.requestPermissions(this as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
    }

    private fun checkNeededPermissions() : Boolean {
        if (ActivityCompat.checkSelfPermission(this as Activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askLocationPermission()
            return false
        }
        return true
    }

    @SuppressLint("MissingPermission")
    fun initMap() {
        mParkingMarkerManager = ParkingMarkerManager(this.applicationContext, mMap!!)
        mMap?.isMyLocationEnabled = true
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10f, this)
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null)
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
        if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
            mUserLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mUserLocation!!.latitude, mUserLocation!!.longitude), 15.0f))

            DataManager.getParkings(mUserLocation!!, 1000000000).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe (
                    { result ->
                        mParkingMarkerManager!!.addParkings(result)
                    },
                    { e ->
                    })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMap()
                }
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()
        this.map_view.onResume()
    }

    override fun onLocationChanged(p0: Location?) {
        mUserLocation = p0
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mUserLocation!!.latitude, mUserLocation!!.longitude), 15.0f))
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onProviderDisabled(p0: String?) {
    }

}
