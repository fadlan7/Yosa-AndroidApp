package com.yosa.ui.yogalist

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yosa.data.model.YogaResponse
import com.yosa.data.setting.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YogaListViewModel : ViewModel() {
    val yogaData = MutableLiveData<YogaResponse>()
    val load = MutableLiveData(View.VISIBLE)
    val error = MutableLiveData<String>()

//    fun getYogaData(username: String) {
//        val client = ApiConfig.getApiService().getUser(username)
//
//        client.enqueue(object : Callback<YogaResponse> {
//            @SuppressLint("NullSafeMutableLiveData")
//            override fun onResponse(
//                call: Call<YogaResponse>,
//                response: Response<YogaResponse>,
//            ) {
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    yogaData.postValue(data)
//                    load.postValue(View.GONE)
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()} ")
//                    error.postValue(response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<YogaResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure: $t")
//                error.postValue(t.message)
//            }
//        })
//    }

    companion object {
        const val TAG = "DetailViewModel"
    }
}