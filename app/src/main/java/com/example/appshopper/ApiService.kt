package com.example.appshopper

import com.example.appshopper.model.Users
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
  //  @GET("/users")
  //  fun fetchAllUsers():Call<List<Users>>

    @GET("/photos")
    fun fetchAllUsers():Call<List<Users>>
}