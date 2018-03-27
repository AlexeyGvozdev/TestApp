package com.example.alexey.testapp.restservice

import com.example.alexey.testapp.model.Article
import com.example.alexey.testapp.model.Category
import io.reactivex.Observable


/**
 * Created by alexey on 28.03.18.
 */
class SearchRepository(private val restApi: RestApi) {

    fun searchCategory(nameCategory: String): Observable<Category> =
            restApi.getDateCategory(nameCategory)

    fun searchArticle(nameArticle: String): Observable<Article> =
            restApi.getArticle(nameArticle)

}