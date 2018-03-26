package com.example.alexey.testapp

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by alexey on 26.03.18.
 */
class ApiFactory {
    companion object {
        fun getRestApiService(): RestApi = buildRetrofit().create(RestApi::class.java)

        private fun buildRetrofit(): Retrofit = Retrofit.Builder()
                .baseUrl("http://mikonatoruri.win/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}