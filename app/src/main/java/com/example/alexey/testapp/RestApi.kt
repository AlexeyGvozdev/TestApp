package com.example.alexey.testapp

import com.example.alexey.testapp.model.Article
import com.example.alexey.testapp.model.Category
import com.example.alexey.testapp.model.Events
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    @GET("list.php")
    fun getDateCategory(@Query("category") category: String): Observable<Category>

    @GET("./articles")
    fun getArticle(): Observable<Article>
}