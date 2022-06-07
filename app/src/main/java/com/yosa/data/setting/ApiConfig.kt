package com.yosa.data.setting

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        private const val PERSONAL_TOKEN = "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"
        private const val BASE_URL = "https://api.github.com"

        fun getApiService(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor {
                    val original = it.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("Authorization", PERSONAL_TOKEN)
                    val request = requestBuilder.build()
                    it.proceed(request)
                }
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}