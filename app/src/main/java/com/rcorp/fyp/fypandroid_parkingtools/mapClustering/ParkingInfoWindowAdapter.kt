package com.rcorp.fyp.fypandroid20.mapClustering

import android.content.Context
import android.util.Log
import android.view.View
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import android.view.LayoutInflater
import com.rcorp.fyp.fypandroid_parkingtools.R
import com.rcorp.fyp.fypandroid_parkingtools.data.DataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.parking_infowindow_view.view.*


/**
 * Created by romai on 20/10/2017.
 */
class ParkingInfoWindowAdapter(context : Context) : InfoWindowAdapter {

    private val view: View
    private var currentMarker : Marker? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.parking_infowindow_view, null)
    }

    override fun getInfoContents(marker: Marker): View? {
        if (marker.title == null)
            return null
        if (currentMarker != null && marker == currentMarker)
            return view
        currentMarker = marker
        DataManager.getParkingInfo(marker.title).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe (
                { result ->
                    view.parking_info_title.text = result.address
                    view.parking_info_desc.text = result.text
                    if (marker.isInfoWindowShown)
                        marker.showInfoWindow()
                },
                { e ->
                    Log.e("ERROR" , e.message)
                    //TODO snake bar d'erreur jolie
                })
        view.parking_info_title.text = ""
        view.parking_info_desc.text = ""
        return view
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}