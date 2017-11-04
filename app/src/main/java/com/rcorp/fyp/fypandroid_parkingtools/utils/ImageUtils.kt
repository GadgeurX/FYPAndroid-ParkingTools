package com.rcorp.fyp.fypandroid20.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.R.attr.bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.widget.TextView


/**
 * Created by romai on 19/10/2017.
 */
object ImageUtils {

    class LoadUrlImage(var bmImage: ImageView) : AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val urldisplay = urls[0]
            var mIcon11: Bitmap? = null
            try {
                val `in` = java.net.URL(urldisplay).openStream()
                mIcon11 = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                try {
                    val `in` = java.net.URL("https://www.buira.org/assets/images/shared/default-profile.png").openStream()
                    mIcon11 = BitmapFactory.decodeStream(`in`)
                } catch (e: Exception) {
                    Log.e("Error", e.message)
                    e.printStackTrace()
                    return null
                }
            }
            return mIcon11
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result != null)
                bmImage.setImageBitmap(result)
        }
    }

}