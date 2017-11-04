package com.rcorp.fyp.fypandroid20.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by romai on 19/10/2017.
 */
object DateUtils {

    fun getDateFromTimestamp(timestamp : Long) : String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    }
}