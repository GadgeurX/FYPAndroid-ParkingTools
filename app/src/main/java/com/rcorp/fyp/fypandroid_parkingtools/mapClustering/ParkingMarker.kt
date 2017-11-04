package com.rcorp.fyp.fypandroid20.mapClustering

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Created by romai on 19/10/2017.
 */
class ParkingMarker(val mId : String, val mLocation : LatLng) : ClusterItem {

    override fun getPosition(): LatLng {
        return mLocation
    }
}