package com.android.onetachi.protocol

import com.android.onetachi.data.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitNetwork {
    // User Login
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/user/login")
    fun loginUser(@Body user : User) : Call<User>

    // User signup
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/auth/registerRequest")
    fun signupUser(@Body user : SignupUser) : Call<SignupResult>

    // User available paper list req
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/auth/list")
    fun availablePaper(@Body user : User) : Call<Papers>
}