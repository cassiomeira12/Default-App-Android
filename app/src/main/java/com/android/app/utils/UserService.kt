package com.android.app.utils

import com.android.app.data.model.BaseUser
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("users")
    fun list(@Header("auth-token") token: String): Call<List<BaseUser>>

    @POST("auth/login")
    fun login(@Body json: Map<String, String>): Call<BaseUser>

    @POST("auth/register")
    fun criar(@Body user: BaseUser): Call<BaseUser>

}