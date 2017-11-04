package com.rcorp.fyp.fypandroid20.utils

import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Created by romai on 19/10/2017.
 */
object SSLCertUtils {

    fun installSSLCertifates(): Boolean {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate>? {
                    return null
                }

                override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
            })

            // Install the all-trusting trust manager
            val sc = SSLContext.getInstance("SSL")
            sc.init(null, trustAllCerts, java.security.SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)

            // Create all-trusting host name verifier
            val allHostsValid = HostnameVerifier { hostname, session -> true }

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)
        } catch (e: Exception) {
            //            Log.d(mContext.getString(R.string.app_name), e.toString());
            return false
        }

        return true
    }
}