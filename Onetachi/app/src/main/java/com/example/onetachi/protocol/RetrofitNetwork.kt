package com.example.onetachi.protocol

import com.example.onetachi.data.LoginUser
import com.example.onetachi.data.QrData
import com.example.onetachi.data.SignupUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitNetwork {
    // User Login
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/user/login")
    fun loginUser(@Body user : LoginUser) : Call<LoginUser>

    // User signup
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/user/signup")
    fun signupUser(@Body user : SignupUser) : Call<SignupUser>

    //qr scan
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/qr/scan")
    fun qrScan(@Body id : QrData) : Call<QrData>
}