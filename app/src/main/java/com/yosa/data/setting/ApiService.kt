package com.yosa.data.setting

import com.yosa.data.model.LevelResponse
import com.yosa.data.model.PoseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
//    @GET("api/levels/{id}")
//    fun getLevels(
//        @Path("id") id: String
//    ): Call<LevelResponse>
//
//    @GET("api/poses/{id}")
//    fun getPoses(
//        @Path("id") id: String
//    ): Call<PoseResponse>

    @GET("api/levels")
    fun getLevels(): Call<LevelResponse>

    @GET("api/poses")
    fun getPoses(
    ): Call<PoseResponse>
}