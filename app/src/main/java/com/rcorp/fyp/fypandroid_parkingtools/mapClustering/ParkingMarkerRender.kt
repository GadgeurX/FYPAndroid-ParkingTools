package com.rcorp.fyp.fypandroid20.mapClustering

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

/**
 * Created by romai on 19/10/2017.
 */
class ParkingMarkerRender(val mContext : Context, val mMap : GoogleMap, val mClusterManager : ClusterManager<ParkingMarker>) : DefaultClusterRenderer<ParkingMarker>(mContext, mMap, mClusterManager) {

    private val mClusterIconGenerator: IconGenerator = IconGenerator(mContext)
    private val mIconParking: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(resizeMapIcons("parking_marker", 75, 75))

    override fun onBeforeClusterItemRendered(parkingMarker: ParkingMarker?, markerOptions: MarkerOptions?) {
        //markerOptions.title((parkingMarker.getAddress() != null ? parkingMarker.getAddress(): String.valueOf(parkingMarker.getPosition().longitude) + " , " + String.valueOf(parkingMarker.getPosition().latitude)));
        markerOptions!!.icon(mIconParking)
        markerOptions.title(parkingMarker?.mId)
    }

    fun getParkingIcon(): BitmapDescriptor {
        return mIconParking
    }


    override fun onBeforeClusterRendered(cluster: Cluster<ParkingMarker>, markerOptions: MarkerOptions) {
        val icon = mClusterIconGenerator.makeIcon(cluster.size.toString())
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }

    /*protected override fun shouldRenderAsCluster(cluster: Cluster<*>): Boolean {
        return cluster.size > 8
    }*/

    private fun resizeMapIcons(iconName: String, width: Int, height: Int): Bitmap {
        val imageBitmap = BitmapFactory.decodeResource(mContext.resources, mContext.resources.getIdentifier(iconName, "drawable", mContext.packageName))
        val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false)
        return resizedBitmap
    }
}