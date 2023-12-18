package com.teacomputers.task.trending.data.remote

import android.util.Log
import com.teacomputers.task.trending.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    companion object {

        private var retrofitInstance: ApiService? = null

        private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        fun getInstance(): ApiService? {
            try {
                if (retrofitInstance == null) {
                    val baseURL = Constants.BASE_URL
                    val retrofit = Retrofit.Builder()
                        .baseUrl(baseURL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build()
                    retrofitInstance = retrofit.create(ApiService::class.java)
                }
            }catch (e :Exception){
                Log.e("MovieViewModel", "Repo Error: $e")
            }

            return retrofitInstance
        }
    }

}