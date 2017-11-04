package com.rcorp.fyp.fypandroid20.utils

import android.location.Location

/**
 * Created by romai on 18/10/2017.
 */
object LocationUtils {

    fun getJsonFromLocation(location: Location) : String {
        return "[" + location.latitude.toString() + "," + location.longitude.toString() + "]"
    }
}