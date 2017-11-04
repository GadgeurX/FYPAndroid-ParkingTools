package com.rcorp.fyp.fypandroid_parkingtools.data.remote

import com.rcorp.fyp.fypandroid20.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.http.Multipart



/**
 * Created by romai on 17/10/2017.
 */
interface RemoteApi {
    @GET("/login/default")
    fun defaultLogin(@Query("user") user: String,
               @Query("pwd") pwd: String)
            : Call<LoginResponse>

    @GET("/login/google")
    fun googleLogin(@Query("token") user: String)
            : Call<GoogleLoginResponse>

    @GET("/user/parkings")
    fun getParkings(@Query("token") token : String, @Query("location") location : String, @Query("radius") radius : String) : Call<ParkingsResponse>

    @GET("/user")
    fun getUserInfo(@Query("token") token : String, @Query("user") user : String) : Call<UserInfoResponse>
    @GET("/user")
    fun getCurrentUserInfo(@Query("token") token : String) : Call<UserInfoResponse>

    @GET("/user/parking_info")
    fun getParkingInfo(@Query("token") token : String, @Query("id") id : String) : Call<ParkingInfoResponse>
  
    @GET("/user/setting")
    fun getUserSetting(@Query("token") token : String, @Query("key") key : String) : Call<MutableMap<String, String>>

    @FormUrlEncoded
    @POST("/user/setting")
    fun setUserSetting(@Field("token") token : String, @Field("key") key : String, @Field("value") value : String) : Call<HttpResponse>

    @FormUrlEncoded
    @POST("/user/first_name")
    fun setUserFirstName(@Field("token") token : String, @Field("name") name : String) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/user/last_name")
    fun setUserLastName(@Field("token") token : String, @Field("name") name : String) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/user/sex")
    fun setUserSex(@Field("token") token : String, @Field("sex") sex : String) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/user/birthday")
    fun setUserBirthday(@Field("token") token : String, @Field("birthday") birthday : Long) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/user")
    fun registerUser(@Field("user") user: String, @Field("email") email : String, @Field("pwd") pwd : String) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/user/parked")
    fun parked(@Field("token") token : String, @Field("location") location : String) : Call<ResponseBody>

    @Multipart
    @PUT("/user/picture")
    fun uploadPicture(@Part("token") token: RequestBody, @Part("picture\"; filename=\"pp.png\" ") file: RequestBody): Call<ResponseBody>
}
