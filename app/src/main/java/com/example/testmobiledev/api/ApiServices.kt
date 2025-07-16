package com.example.testmobiledev.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("users")
    fun getUsers(
        @Query("page") page: Int
    ): Call<UserResponse>
}
