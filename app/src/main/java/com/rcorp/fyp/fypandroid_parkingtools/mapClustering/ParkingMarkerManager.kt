package com.rcorp.fyp.fypandroid20.mapClustering

import android.content.Context
import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.heatmaps.WeightedLatLng
import com.rcorp.fyp.fypandroid20.model.Parking
import com.google.android.gms.maps.model.TileOverlayOptions
import com.rcorp.fyp.fypandroid_parkingtools.heatmap.FYPHeatmapTileProvider
import com.rcorp.fyp.fypandroid_parkingtools.heatmap.Gradient
import java.util.*


/**
 * Created by romai on 19/10/2017.
 */
class ParkingMarkerManager (context : Context, map : GoogleMap){
    private val mClusterManager: ClusterManager<ParkingMarker> = ClusterManager<ParkingMarker>(context, map)
    private val mParkingRender: ParkingMarkerRender = ParkingMarkerRender(context, map, mClusterManager)
    private val mParkingIdList: MutableList<String> = arrayListOf()
    private val mParkingZone : MutableList<WeightedLatLng> = arrayListOf()
    private val mMap : GoogleMap = map

    init {
        mClusterManager.renderer = mParkingRender
        //map.setOnMarkerClickListener(mClusterManager)
        //map.setOnInfoWindowClickListener(mClusterManager)
        //mClusterManager.setOnClusterClickListener(this);
        //mClusterManager.setOnClusterInfoWindowClickListener(this);
        //mClusterManager.setOnClusterItemClickListener(this);
        //mClusterManager.setOnClusterItemInfoWindowClickListener(this);
    }

    fun addParking(parking : Parking) {
        if (mParkingIdList.size == 0 || !mParkingIdList.contains(parking._id)) {
            mClusterManager.addItem(ParkingMarker(parking._id, LatLng(parking.location[0].toDouble(), parking.location[1].toDouble())))
            mParkingIdList.add(parking._id)
        }
    }

    fun addParkingZone(parking : Parking) {
        var value = 0.1
        val random = Random()
        if (parking.indices != null)
            for ((_, map) in parking.indices) {
                value = 1.0 - map["current_index"]!!
                if (value <= 0.1)
                    value = 0.1
                mParkingZone.add(WeightedLatLng(LatLng(parking.location[0].toDouble(), parking.location[1].toDouble()), value))
            }
    }

    fun addParkings(parkings : List<Parking>) {
        parkings.forEach { parking ->
            if (parking.id_module == 0)
                addParkingZone(parking)
        }
        cluster()
        drawHeatMap()
    }

    fun drawHeatMap() {
        val colors = intArrayOf(
                Color.rgb(102, 225, 0), // green
                Color.rgb(255, 0, 0)    // red
        )

        val startPoints = floatArrayOf(0.1f, 1f)

        val gradient = Gradient(colors, startPoints)
        val provider = FYPHeatmapTileProvider.Builder()
                .weightedData(mParkingZone)
                .gradient(gradient)
                .opacity(0.5)
                .radius(50)
                .build()
        mMap.addTileOverlay(TileOverlayOptions().tileProvider(provider))
    }

    fun cluster() {
        mClusterManager.cluster()
    }
}