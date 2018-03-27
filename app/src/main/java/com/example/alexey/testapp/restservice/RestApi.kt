package com.example.alexey.testapp.restservice

import com.example.alexey.testapp.model.Article
import com.example.alexey.testapp.model.Category
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("list.php")
    fun getDateCategory(@Query("category") categoryName: String): Observable<Category>

    @GET("./post.php")
    fun getArticle(@Query("article") articleName: String): Observable<Article>

    companion object Factory{
        fun getRestApiService(): RestApi = buildRetrofit().create(RestApi::class.java)

        private fun buildRetrofit(): Retrofit = Retrofit.Builder()
                .baseUrl("http://mikonatoruri.win/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}