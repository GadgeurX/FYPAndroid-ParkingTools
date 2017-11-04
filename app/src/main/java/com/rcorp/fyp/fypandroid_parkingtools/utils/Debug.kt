package com.rcorp.fyp.fypandroid20.utils

import android.util.Log

/**
 * Created by romai on 22/10/2017.
 */
object Debug {

    fun logError(msg : String?) {
        if (msg != null)
            Log.d("ERROR" , msg)
    }
}