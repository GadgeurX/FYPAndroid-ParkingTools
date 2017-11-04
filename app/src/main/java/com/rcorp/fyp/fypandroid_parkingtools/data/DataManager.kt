package com.rcorp.fyp.fypandroid_parkingtools.data

import android.location.Location
import android.util.Log
import com.rcorp.fyp.fypandroid20.model.HttpResponse
import com.rcorp.fyp.fypandroid20.model.Parking
import com.rcorp.fyp.fypandroid20.model.ParkingInfo
import com.rcorp.fyp.fypandroid20.model.UserInfo
import com.rcorp.fyp.fypandroid20.utils.LocationUtils
import com.rcorp.fyp.fypandroid_parkingtools.data.remote.*
import io.reactivex.Observable
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.ResponseBody
import java.io.File
import okhttp3.RequestBody




/**
 * Created by romai on 17/10/2017.
 */
object DataManager {



    val mRetrofit = Retrofit.Builder()
            .baseUrl("https://api.findyourparking.fr:4000/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpMyClient.getUnsafeOkHttpClient())
            .build()

    val mRemoteSource = mRetrofit.create(RemoteApi::class.java)

    var mToken : String? = null


    fun defaultLogin(user: String, pwd: String) : Observable<Boolean> {
        return Observable.create {
            subscriber ->

            val loginResponse = mRemoteSource.defaultLogin(user, pwd).execute()

            if (loginResponse.isSuccessful) {
                mToken = loginResponse.body().token
                Log.d("Token : ", mToken)
                subscriber.onNext(true)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(loginResponse.message()))
            }
        }
    }

    fun googleLogin(token: String) : Observable<Boolean> {
        return Observable.create {
            subscriber ->

            val googleLoginResponse = mRemoteSource.googleLogin(token).execute()

            if (googleLoginResponse.isSuccessful) {
                mToken = googleLoginResponse.body().token
                Log.d("Token : ", mToken)
                subscriber.onNext(true)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(googleLoginResponse.message()))
            }
        }
    }

    fun getParkings(location: Location, radius: Int) : Observable<List<Parking>> {
        return Observable.create {
            subscriber ->

            val parkingsResponse = mRemoteSource.getParkings(mToken!!, LocationUtils.getJsonFromLocation(location), radius.toString()).execute()

            if (parkingsResponse.isSuccessful) {
                subscriber.onNext(parkingsResponse.body().parkings)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(parkingsResponse.message()))
            }
        }
    }

    fun getParkingInfo(id: String) : Observable<ParkingInfo> {
        return Observable.create {
            subscriber ->

            val parkingResponse = mRemoteSource.getParkingInfo(mToken!!, id).execute()

            if (parkingResponse.isSuccessful) {
                subscriber.onNext(parkingResponse.body().parking)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(parkingResponse.message()))
            }
        }
    }

    fun getUserInfo(user : String) : Observable<UserInfo> {
        return Observable.create {
            subscriber ->

            val userResponse = mRemoteSource.getUserInfo(mToken!!, user).execute()

            if (userResponse.isSuccessful) {
                subscriber.onNext(userResponse.body().info)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(userResponse.message()))
            }
        }
    }

    fun getCurrentUserInfo() : Observable<UserInfo> {
        return Observable.create {
            subscriber ->

            val userResponse = mRemoteSource.getCurrentUserInfo(mToken!!).execute()

            if (userResponse.isSuccessful) {
                subscriber.onNext(userResponse.body().info)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(userResponse.message()))
            }
        }
    }

    fun getUserSetting(key : String) : Observable<MutableMap<String, String>> {
        return Observable.create {
            subscriber ->

            val userResponse = mRemoteSource.getUserSetting(mToken!!, key).execute()

            if (userResponse.isSuccessful) {
                subscriber.onNext(userResponse.body())
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(userResponse.message()))
            }
        }
    }
  
  fun setUserFirstName(first_name : String) : Observable<ResponseBody> {
        return Observable.create {
            subscriber ->

            val response = mRemoteSource.setUserFirstName(mToken!!, first_name).execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun setUserSetting(key : String, value : String) : Observable<HttpResponse> {
        return Observable.create {
            subscriber ->

            val userResponse = mRemoteSource.setUserSetting(mToken!!, key, value).execute()

            if (userResponse.isSuccessful) {
                subscriber.onNext(userResponse.body())
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(userResponse.message()))
            }
        }
    }

  fun setUserLastName(last_name : String) : Observable<ResponseBody> {
        return Observable.create {
            subscriber ->

            val response = mRemoteSource.setUserLastName(mToken!!, last_name).execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun setUserSex(sex : String) : Observable<ResponseBody> {
        return Observable.create {
            subscriber ->

            val response = mRemoteSource.setUserSex(mToken!!, sex).execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun setUserBirthday(birthday : Long) : Observable<ResponseBody> {
        return Observable.create {
            subscriber ->

            val response = mRemoteSource.setUserBirthday(mToken!!, birthday).execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun registerUser(user: String, email : String, pwd : String) : Observable<ResponseBody> {
        return Observable.create {
            subscriber ->

            val response = mRemoteSource.registerUser(user, email, pwd).execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun parked(location: Location) : Observable<ResponseBody> {
        return Observable.create {
            subscriber ->

            val response = mRemoteSource.parked(mToken!!, "[" + location.latitude + "," + location.longitude + "]").execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun uploadPicture(file : File) : Observable<ResponseBody> {
        return Observable.create {
            subscriber ->

            val fileBody = RequestBody.create(MediaType.parse("image/*"), file)
            val tokenBody = RequestBody.create(MediaType.parse("text/plain"), mToken)
            val response = mRemoteSource.uploadPicture(tokenBody, fileBody).execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}