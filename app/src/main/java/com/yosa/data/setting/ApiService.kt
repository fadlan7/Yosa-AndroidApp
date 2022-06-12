package com.yosa.data.setting

import com.yosa.data.model.YogaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{login}")
    fun getUser (
        @Path("login")
        username: String
    ): Call<YogaResponse>
}