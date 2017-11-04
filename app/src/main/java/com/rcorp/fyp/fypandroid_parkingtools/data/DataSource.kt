package com.rcorp.fyp.fypandroid_parkingtools.data

import com.rcorp.fyp.fypandroid20.model.LoginResponse
import retrofit2.Call

/**
 * Created by romai on 17/10/2017.
 */
interface DataSource {
    fun DefaultLogin(user: String, pwd: String) : Call<LoginResponse>
}